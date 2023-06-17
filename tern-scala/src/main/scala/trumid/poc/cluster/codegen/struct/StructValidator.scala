package trumid.poc.cluster.codegen.struct

import trumid.poc.codegen.common.Template
import trumid.poc.common.CamelCaseStyle
import trumid.poc.model.{Domain, Entity, Mode}

class StructValidator(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Validator"
  override protected def getCategory() = "object"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.poc.cluster.ResultCode\n")
  }

  override protected def generateEntity(): Unit = {
    val name = getName
    val category = getCategory

    builder.append(s"${category} ${name}")
    builder.append(" {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {
    generateGetterMethods()
    generateValidationMethod()
  }

  private def generateGetterMethods(): Unit = {
    builder.append("\n")
  }

  private def generateValidationMethod(): Unit = {
    val name = entity.getName(mode)
    val identifier = CamelCaseStyle.toCase(name)

    builder.append("\n")
    builder.append(s"   def validate(${identifier}: ${name}): ResultCode = {\n")
    builder.append("      ResultCode.success(\"Ok\")\n")
    builder.append(s"   }\n")
  }
}
