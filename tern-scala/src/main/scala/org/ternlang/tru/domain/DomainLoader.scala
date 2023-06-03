package org.ternlang.tru.domain

import org.ternlang.common.store.ClassPathStore
import org.ternlang.compile.StoreContext
import org.ternlang.compile.assemble.{ModelScopeBuilder, OperationAssembler}
import org.ternlang.core.module.Path
import org.ternlang.core.scope.{EmptyModel, Scope}
import org.ternlang.parse.{SyntaxCompiler, SyntaxNode}
import org.ternlang.tru
import org.ternlang.tru.domain.DomainLoader.{expression, grammar, instructions, scope}
import org.ternlang.tru.domain.tree.Source
import org.ternlang.tru.model.{Domain, Version}

import java.net.{URI, URL}
import java.util
import java.util.concurrent.ScheduledThreadPoolExecutor

object DomainLoader {
  val instructions = "tru.instruction"
  val grammar = "tru.grammar"
  val expression = "source"
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
    val domain = tru.model.Domain(version, merger.create(model, scope))

    process(resources, domain)
  }

  private def process(resources: Seq[URL], domain: Domain): Domain = {
    var sources: List[Source] = List.empty
    val scope: Scope = domain.scope
    val done = new util.HashSet[URL]()
    val queue = new util.ArrayDeque[URL]()

    resources.foreach(resource => queue.offer(resource))

    while (!queue.isEmpty) {
      val resource: URL = queue.poll()

      if (done.add(resource)) {
        val definition: DomainDefinition = read(resource)
        val location: String = definition.path.getPath

        try {
          val node: SyntaxNode = parser.parse(location, definition.source, expression)
          val source: Source = assembler.assemble(node, definition.path)
          val namespace = source.define(scope, domain)
          val imports = namespace.getImports();

          imports.forEach(path => {
            try {
              val resource = URI.create(path.getPath).toURL()

              if (!done.contains(resource)) {
                queue.offer(resource)
              }
            } catch {
              case e: Throwable =>
                throw new IllegalStateException(s"Could not resolve ${path}", e)
            }
          })
          sources ++= List(source)
        } catch {
          case e: Exception =>
            throw new IllegalStateException("Could not process " + location + "\n: " + definition.source, e)
        }
      }
    }

    for (schema <- sources) {
      val child: Scope = scope.getChild // get a private scope
      schema.include(child, domain)
    }
    for (schema <- sources) {
      schema.process(scope, domain)
    }
    for (schema <- sources) {
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
