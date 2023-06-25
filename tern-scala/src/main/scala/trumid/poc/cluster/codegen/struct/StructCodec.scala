package trumid.poc.cluster.codegen.struct

import trumid.poc.codegen.common.Template
import trumid.poc.model.{Domain, Entity, Mode}

class StructCodec(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = s"${entity.getName(mode)}Codec"

  override protected def getCategory() = "class"

  override protected def generateEntity(): Unit = {
    val category = getCategory()

    generateObject()
    builder.append(s"final ${category} ${getName}(variable: Boolean = true) extends ${entity.getName(mode)}Builder with Flyweight[${getName}] {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.poc.common._\n")
    builder.append("import trumid.poc.common.message._\n")
    builder.append("import trumid.poc.common.array._\n")
    builder.append("import trumid.poc.cluster._\n")
  }

  override protected def generateBody(): Unit = {
    generateFields()
    generateDefaultFields()
    generateAssignMethod()
    generateGetterMethods()
    generateDefaultsMethod()
    generateClearMethod()
    generateResetMethod()
    generateValidationMethod()
  }

  private def generateFields(): Unit = {
    builder.append("\n")
    properties.create(entity).stream.forEach(generator => {
      generator.generateField(builder)
    })
  }

  private def generateDefaultFields(): Unit = {
    builder.append("   private var buffer: ByteBuffer = _\n")
    builder.append("   private var offset: Int = _\n")
    builder.append("   private var length: Int = _\n")
    builder.append("   private var required: Int = _\n")
  }

  private def generateGetterMethods(): Unit = {
    properties.create(entity).stream.forEach(generator => {
      builder.append("\n")
      generator.generateGetter(builder)
      builder.append("\n")
      generator.generateSetter(builder)
    })
  }

  private def generateObject(): Unit = {
    val name = entity.getName(mode)

    builder.append(s"object ${name}Codec {\n")
    builder.append(s"   val VERSION: Int = ${entity.getNamespace.getVersion.version}\n")
    builder.append(s"   val REQUIRED_SIZE: Int = ${entity.getSize.getRequiredSize}\n")
    builder.append(s"   val TOTAL_SIZE: Int = ${entity.getSize.getTotalSize}\n")
    builder.append("}\n\n")
  }

  private def generateAssignMethod(): Unit = {
    builder.append("\n")
    builder.append(s"   override def assign(buffer: ByteBuffer, offset: Int, length: Int): ${getName} = {\n")
    builder.append(s"      val required = if(variable) ${getName}.REQUIRED_SIZE else ${getName}.TOTAL_SIZE\n\n")
    builder.append("      if(length < required) {\n")
    builder.append("         throw new IllegalArgumentException(\"Length is \" + length + \" but must be at least \" + required);\n")
    builder.append("      }\n")
    builder.append("      this.buffer = buffer;\n")
    builder.append("      this.offset = offset;\n")
    builder.append("      this.length = length;\n")
    builder.append("      this.required = required;\n")
    builder.append("      this;\n")
    builder.append("   }\n")
  }

  private def generateDefaultsMethod(): Unit = {
    builder.append("\n")
    builder.append(s"   override def defaults(): ${getName} = {\n")

    entity.getProperties().forEach(property => {
      val identifier = property.getName()

      if(property.isEntity()) {
        builder.append(s"      ${identifier}Codec.defaults()\n")
      }
    })
    builder.append("      this\n")
    builder.append("   }\n")
  }

  private def generateClearMethod(): Unit = {
    builder.append("\n")
    builder.append(s"   override def clear(): ${getName} = {\n")

    entity.getProperties().forEach(property => {
      val identifier = property.getName()

      if(property.isEntity() || property.isArray()) {
        builder.append(s"      ${identifier}Codec.clear()\n")
      }
    })
    builder.append("      this\n")
    builder.append("   }\n")
  }

  private def generateResetMethod(): Unit = {
    builder.append("\n")
    builder.append(s"   override def reset(): ${getName} = {\n")

    entity.getProperties().forEach(property => {
      val identifier = property.getName()

      if(property.isEntity() || property.isArray()) {
        builder.append(s"      ${identifier}Codec.reset()\n")
      }
    })
    builder.append("      this\n")
    builder.append("   }\n")
  }

  private def generateValidationMethod(): Unit = {
    builder.append("\n")
    builder.append(s"   override def validate(): ResultCode = {\n")
    builder.append(s"      ${entity.getName(mode)}Validator.validate(this)\n")
    builder.append(s"   }\n")
  }
}
