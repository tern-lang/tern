package org.ternlang.scala.domain.tree

import org.ternlang.core.Compilation
import org.ternlang.core.module.{Module, Path}
import org.ternlang.tree.Qualifier

case class NamespaceCompilation(qualifier: Qualifier) extends Compilation {

  override def compile(module: Module, path: Path, line: Int): Namespace = {
    Namespace(qualifier, path)
  }

}