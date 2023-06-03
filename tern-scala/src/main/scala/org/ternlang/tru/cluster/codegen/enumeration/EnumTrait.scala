package org.ternlang.tru.cluster.codegen.enumeration

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.common.{PascalCaseStyle, SnakeCaseStyle}
import org.ternlang.tru.model.{Domain, Entity, Mode, Property}

class EnumTrait(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = s"${entity.getName(mode)}"

  override protected def getCategory() = "sealed trait"

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    generateEnumObject()
    builder.append(s"${category} ${name} {\n")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {
    entity.getProperties().forEach(property => {
      generateEnumCheck(property)
    })
    builder.append("   def toCode(): Byte\n")
    entity.getProperties().forEach(property => {
      generateEnumValue(property)
    })
  }

  private def generateEnumObject(): Unit = {
    val name = entity.getName(mode)

    builder.append(s"object ${name} {\n")
    builder.append(s"   def resolve(code: Byte): ${name} = code match {\n")

    entity.getProperties().forEach(property => {
      val value = SnakeCaseStyle.toCase(property.getName)
      builder.append(s"      case ${property.getIndex} => ${name}.${value.toUpperCase}\n")
    })
    builder.append("      case _ => throw new IllegalArgumentException(\"" + name + " code not matched for \" + code)\n")
    builder.append(s"   }\n")
    builder.append("}\n\n")
  }

  private def generateEnumCheck(property: Property): Unit = {
    val identifier = PascalCaseStyle.toCase(property.getName)
    builder.append(s"   def is${identifier}(): Boolean = false\n")
  }

  private def generateEnumValue(property: Property): Unit = {
    val name = SnakeCaseStyle.toCase(property.getName)
    val check = PascalCaseStyle.toCase(property.getName)

    builder.append("\n")
    builder.append(s"   object ${name.toUpperCase} extends ${getName} {\n")
    builder.append(s"      override def is${check}(): Boolean = true\n")
    builder.append(s"      override def toCode(): Byte = ${property.getIndex}\n")
    builder.append("      override def toString(): String = \"" + name.toUpperCase + "\"\n")
    builder.append("   }\n")
  }
}
