package trumid.poc.dsl.tree.imports

import org.ternlang.core.Compilation
import org.ternlang.core.module.{Module, Path}

import java.net.URI
import java.util.function.Predicate
import java.util.{Collections, Set}

class WildQualifier(resource: URI) extends Compilation {

  override def compile(module: Module, path: Path, line: Int): Qualifier = {
    CompileResult(resource.toString)
  }

  private case class CompileResult(location: String) extends Qualifier {
    override def getLocation(): String = location
    override def getPredicate(): Predicate[String] = _ => true
    override def getEntities(): Set[String] = Collections.emptySet()
  }
}
