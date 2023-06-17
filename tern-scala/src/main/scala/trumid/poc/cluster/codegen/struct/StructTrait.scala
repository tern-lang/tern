package trumid.poc.cluster.codegen.struct

import trumid.poc.codegen.common.Template
import trumid.poc.model.{Domain, Entity, Mode}

class StructTrait(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode)
  override protected def getCategory() = "trait"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.poc.cluster.ResultCode\n")
  }

  override protected def generateEntity(): Unit = {
    val name = getName
    val category = getCategory
    val extend = entity.getExtends(mode)

    if (extend != null) {
      builder.append(s"${category} ${name} extends ${extend}")
    } else {
      builder.append(s"${category} ${name}")
    }
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
    properties.create(entity).stream.forEach(generator => {
      generator.generateGetterSignature(builder)
    })
  }

  private def generateValidationMethod(): Unit = {
    builder.append("   def validate(): ResultCode\n")
  }
}
