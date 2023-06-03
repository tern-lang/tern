package org.ternlang.tru.cluster.codegen.struct

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.model.{Domain, Entity, Mode}

class StructCodec(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = s"${entity.getName(mode)}Codec"

  override protected def getCategory() = "class"

  override protected def generateBody(): Unit = {
  }
}
