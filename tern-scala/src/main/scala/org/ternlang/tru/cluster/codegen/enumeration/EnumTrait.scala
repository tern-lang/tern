package org.ternlang.tru.cluster.codegen.enumeration

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.model.{Domain, Entity, Mode}

class EnumTrait(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = s"${entity.getName(mode)}"

  override protected def getCategory() = "sealed trait"

  override protected def generateBody(): Unit = {

  }
}
