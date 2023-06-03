package org.ternlang.tru.dsl.tree.imports

import org.ternlang.core.Compilation
import org.ternlang.core.module.{Module, Path}

import java.net.URI
import java.util.Set
import java.util.function.Predicate

class FullQualifier(types: Set[String], resource: URI) extends Compilation {

  override def compile(module: Module, path: Path, line: Int): Qualifier = {
    CompileResult(types, resource.toString)
  }

  private case class CompileResult(types: Set[String], location: String) extends Qualifier {
    override def getLocation(): String = location
    override def getPredicate(): Predicate[String] = name => types.contains(name)
  }
}
