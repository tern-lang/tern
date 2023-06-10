package org.ternlang.tru.cluster.codegen.struct

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.model.{Domain, Entity, Mode}

class StructArray(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Array"
  override protected def getCategory() = "trait"

  override protected def generateExtraImports(): Unit = {
    builder.append("import org.ternlang.tru.common.array._\n")
  }

  override protected def generateEntity(): Unit = {
    generateBody()
  }

  override protected def generateBody(): Unit = {
    val name = entity.getName(mode)
    val category = getCategory()

    builder.append(s"${category} ${name}Array extends GenericArray[${name}] {}\n")
    builder.append(s"${category} ${name}ArrayBuilder extends GenericArrayBuilder[${name}, ${name}Builder] {}\n")
  }
}
