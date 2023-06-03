package org.ternlang.tru.dsl.tree

import org.ternlang.common.Array
import org.ternlang.core.Compilation
import org.ternlang.core.module.{Module, Path}
import org.ternlang.parse.StringToken
import org.ternlang.tru.dsl.tree.imports.ResourcePath

case class SourceNamespace(qualifier: Array[StringToken]) extends Compilation {

  override def compile(module: Module, path: Path, line: Int): SourceFile = {
    val builder = new StringBuilder()
    val file = new Path(ResourcePath.resolve(path))

    qualifier.forEach(entry => {
      if (builder.length > 0) {
        builder.append(".")
      }
      builder.append(entry.getValue)
    })
    new SourceFile(builder.toString, file)
  }
}
