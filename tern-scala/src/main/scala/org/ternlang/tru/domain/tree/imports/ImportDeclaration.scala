package org.ternlang.tru.domain.tree.imports

import org.ternlang.core.Compilation
import org.ternlang.core.module.{Module, Path}

case class ImportDeclaration(qualifier: Qualifier) extends Compilation {

  override def compile(module: Module, path: Path, line: Int): Import = {
    new Import(qualifier, path)
  }
}
