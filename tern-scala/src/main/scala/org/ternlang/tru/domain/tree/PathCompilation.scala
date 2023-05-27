package org.ternlang.tru.domain.tree

import org.ternlang.core.Compilation
import org.ternlang.core.module.{Module, Path}
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral

import java.net.URI

class PathCompilation(literal: TextLiteral) extends Compilation {

  override def compile(module: Module, path: Path, line: Int): URI = {
    val resource = new NameReference(literal).getName(module.getScope)
    PathCompilation.relative(path, resource)
  }
}

object PathCompilation {

  def relative(path: Path, resource: String): URI = {
    var from: String = resolve(path);

    if(from.contains("/")) {
      from = from.substring(0, from.lastIndexOf('/') + 1)
    } else {
      from = ""
    }
    URI.create(from + resource).normalize()
  }

  private def resolve(path: Path): String = {
    var resource: String = path.getPath

    if (resource.startsWith("/")) {
      resource = resource.substring(1)
    }
    if (resource.endsWith("/tru.tern")) {
      resource = resource.replace("/tru.tern", ".tru")
    }
    resource
  }
}