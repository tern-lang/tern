package org.ternlang.tru.domain.tree.imports

import org.ternlang.core.Compilation
import org.ternlang.core.Reserved._
import org.ternlang.core.module.{Module, Path}
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.domain.Constants._

import java.net.URI

class RelativePath(literal: TextLiteral) extends Compilation {

  override def compile(module: Module, path: Path, line: Int): URI = {
    val resource = new NameReference(literal).getName(module.getScope)
    RelativePath.relative(path, resource)
  }
}

object RelativePath {

  def relative(path: Path, resource: String): URI = {
    val from: String = resolve(path);
    val index = from.lastIndexOf('/')

    if (index >= 0) {
      URI.create(from.substring(0, index + 1) + resource).normalize()
    } else {
      URI.create(resource).normalize()
    }
  }

  def resolve(path: Path): String = {
    val resource: String = if(path.getPath.startsWith("/")) {
      path.getPath.substring(1)
    } else {
      path.getPath
    }
    if (resource.endsWith(s"/${EXTENSION}${SCRIPT_EXTENSION}")) {
      resource.replace(s"/${EXTENSION}${SCRIPT_EXTENSION}", s".${EXTENSION}")
    } else {
      resource
    }
  }
}