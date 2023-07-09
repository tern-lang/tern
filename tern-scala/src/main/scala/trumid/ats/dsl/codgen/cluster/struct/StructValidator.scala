package trumid.ats.dsl.codgen.cluster.struct

import trumid.poc.codegen.common.Template
import trumid.poc.common.CamelCaseStyle
import trumid.poc.model.{Domain, Entity, Mode}

class StructValidator(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Validator"
  override protected def getCategory() = "object"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.ats.cluster.common.ResultCode\n")
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
    val properties = entity.getProperties()
    val parameter = CamelCaseStyle.toCase(entity.getName)

    builder.append("\n")
    builder.append(s"   def validate(${identifier}: ${name}): ResultCode = {\n")
    validations.create(entity).forEach(validation => {
      validation.generateValidation(builder)
    })
    properties.forEach(property => {
      if(property.isEntity() && !property.isArray()) {
        val constraint = property.getConstraint()
        val category = entity.getNamespace().getVisibleEntity(constraint).getCategory()

        if(!category.isEnum()) {
          val identifier = property.getName()

          builder.append(s"      if(!${parameter}.${identifier}().validate().success()) {\n")
          builder.append(s"         return ${parameter}.${identifier}().validate()\n")
          builder.append(s"      }\n")
        }
      }
    })
    builder.append("      ResultCode.OK\n")
    builder.append(s"   }\n")
  }
}
