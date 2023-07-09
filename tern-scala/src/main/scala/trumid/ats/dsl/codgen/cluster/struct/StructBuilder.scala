package trumid.ats.dsl.codgen.cluster.struct

import trumid.poc.codegen.common.Template
import trumid.poc.model.{Domain, Entity, Mode}

class StructBuilder(domain: Domain, entity: Entity, mode: Mode)  extends Template(domain, entity, mode) {

  override protected def getName(): String = s"${entity.getName(mode)}Builder"

  override protected def getCategory() = "trait"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.ats.cluster.common.array._\n")
    builder.append("import trumid.ats.cluster.common.OptionBuilder\n")
  }

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    builder.append(s"${category} ${name}Builder extends ${name} {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {
    builder.append("\n")
    generateSetterMethods()
    generateDefaultsMethod()
    generateClearMethod()
  }

  private def generateSetterMethods(): Unit = {
    properties.create(entity).stream.forEach(generator => {
      generator.generateSetterSignature(builder)
    })
  }

  private def generateDefaultsMethod(): Unit = {
    builder.append(s"   def defaults(): ${getName}\n")
  }

  private def generateClearMethod(): Unit = {
    builder.append(s"   def clear(): ${getName}\n")
    builder.append("\n")
  }
}
