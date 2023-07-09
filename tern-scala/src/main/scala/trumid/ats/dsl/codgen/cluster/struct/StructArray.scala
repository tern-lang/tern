package trumid.ats.dsl.codgen.cluster.struct

import trumid.poc.codegen.common.Template
import trumid.poc.model.{Domain, Entity, Mode}

class StructArray(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Array"
  override protected def getCategory() = "trait"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.ats.cluster.common.array._\n")
  }

  override protected def generateEntity(): Unit = {
    generateBody()
  }

  override protected def generateBody(): Unit = {
    val name = entity.getName(mode)
    val category = getCategory()

    builder.append(s"${category} ${name}Array extends GenericArray[${name}] {}\n\n")
    builder.append(s"${category} ${name}ArrayBuilder extends GenericArrayBuilder[${name}, ${name}Builder] with ${name}Array {\n")
    builder.append(s"   def reset(): ${name}ArrayBuilder\n")
    builder.append(s"   def clear(): ${name}ArrayBuilder\n")
    builder.append(s"}\n")
  }
}
