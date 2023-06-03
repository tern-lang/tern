package org.ternlang.tru.cluster.codegen.struct

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.model.{Domain, Entity, Mode}

class StructCodec(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = s"${entity.getName(mode)}Codec"

  override protected def getCategory() = "class"

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    builder.append(s"object ${name}Codec {\n")
    builder.append(s"   val VERSION: Int = ${entity.getNamespace.getVersion.version}\n")
    builder.append(s"   val REQUIRED_SIZE: Int = ${entity.getSize.getRequiredSize}\n")
    builder.append(s"   val TOTAL_SIZE: Int = ${entity.getSize.getTotalSize}\n")
    builder.append("}\n\n")
    builder.append(s"${category} ${name}Codec extends ${name}Builder with Flyweight[${name}Codec] {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {

  }
}
