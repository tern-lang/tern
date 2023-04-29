package org.ternlang.scala.domain

import org.ternlang.common.store.ClassPathStore
import org.ternlang.compile.StoreContext
import org.ternlang.compile.assemble.{ModelScopeBuilder, OperationAssembler}
import org.ternlang.core.module.Path
import org.ternlang.core.scope.{EmptyModel, Scope}
import org.ternlang.parse.{SyntaxCompiler, SyntaxNode}
import org.ternlang.scala.domain.DomainLoader.{expression, grammar, instructions, scope}
import org.ternlang.scala.domain.tree.Schema

import java.net.URL
import java.util.concurrent.ScheduledThreadPoolExecutor

object DomainLoader {
  val instructions = "idl.instruction"
  val grammar = "idl.grammar"
  val expression = "schema"
  val scope = "default"
}

class DomainLoader(version: Version) {
  private val store = new ClassPathStore
  private val context = new StoreContext(store)
  private val executor = new ScheduledThreadPoolExecutor(1)
  private val assembler = new OperationAssembler(context, executor, instructions)
  private val parser = new SyntaxCompiler(grammar).compile
  private val merger = new ModelScopeBuilder(context)
  private val aligner = new PropertyAligner
  private val processor = new DomainProcessor

  def load(resources: Seq[URL]): Domain = {
    val model = new EmptyModel
    val domain = Domain(version, merger.create(model, scope))

    process(resources, domain)
  }

  private def process(resources: Seq[URL], domain: Domain): Domain = {
    var schemas: List[Schema] = List.empty
    val scope: Scope = domain.scope

    for (resource <- resources) {
      val definition: DomainDefinition = read(resource)
      val location: String = definition.path.getPath
      try {
        val node: SyntaxNode = parser.parse(location, definition.source, expression)
        val schema: Schema = assembler.assemble(node, definition.path)
        schema.define(scope, domain)
        schemas ++= List(schema)
      } catch {
        case e: Exception =>
          throw new IllegalStateException("Could not process " + location + "\n: " + definition.source, e)
      }
    }

    for (schema <- schemas) {
      val child: Scope = scope.getChild // get a private scope
      schema.include(child, domain)
    }
    for (schema <- schemas) {
      schema.process(scope, domain)
    }
    for (schema <- schemas) {
      schema.extend(scope, domain)
    }
    processor.process(domain) // generate commands and substitute primary keys
    aligner.align(domain) // calculate lengths and offsets
    domain
  }

  private def read(resource: URL): DomainDefinition = {
    val location: String = resource.toString
    try {
      val path: Path = new Path(location)
      val body = scala.io.Source.fromURL(resource).mkString
      // process documentation here
      DomainDefinition(path, body)
    } catch {
      case e: Exception =>
        throw new IllegalStateException("Could not read " + resource, e)
    }
  }

  case class DomainDefinition(path: Path, source: String)
}
