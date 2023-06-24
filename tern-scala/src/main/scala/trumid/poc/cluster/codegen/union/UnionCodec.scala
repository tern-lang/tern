package trumid.poc.cluster.codegen.union

import trumid.poc.codegen.common.Template
import trumid.poc.common.message.ByteSize
import trumid.poc.common.{PascalCaseStyle, SnakeCaseStyle}
import trumid.poc.model.{Domain, Entity, Mode, Property}

import java.util

class UnionCodec(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

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
    generateMatchMethod()
    generateGetterMethods()
    generateDefaultsMethod()
    generateClearMethod()
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
    val name = entity.getName(mode)
    val properties: util.List[Property] = entity.getProperties
    val size = entity.getSize

    builder.append("\n")
    properties.forEach(property => {
      val identifier: String = property.getName()
      val constraint: String = property.getConstraint(mode)
      val code = SnakeCaseStyle.toCase(identifier).toUpperCase
      val method: String = PascalCaseStyle.toCase(identifier)

      builder.append(s"   def ${identifier}(): ${constraint}Codec = {\n")
      builder.append(s"      this.buffer.setByte(this.offset, ${name}Codec.${code}_ID)\n")
      builder.append(s"      this.buffer.setCount(this.offset + ${name}Codec.HEADER_SIZE + this.required)\n")
      builder.append(s"      this.${identifier}Codec.assign(this.buffer, this.offset + ${name}Codec.HEADER_SIZE, this.length - ${name}Codec.HEADER_SIZE)\n")
      builder.append(s"   }\n\n")
      builder.append(s"   def is${method}(): Boolean = {\n")
      builder.append(s"      this.buffer.getByte(this.offset) == ${name}Codec.${code}_ID\n")
      builder.append(s"   }\n\n")
    })
  }

  private def generateObject(): Unit = {
    val name = entity.getName(mode)
    val properties = entity.getProperties()

    builder.append(s"object ${name}Codec {\n")
    builder.append(s"   val VERSION: Int = ${entity.getNamespace.getVersion.version}\n")
    builder.append(s"   val REQUIRED_SIZE: Int = ${entity.getSize.getRequiredSize}\n")
    builder.append(s"   val TOTAL_SIZE: Int = ${entity.getSize.getTotalSize}\n")
    builder.append(s"   val HEADER_SIZE: Int = ${ByteSize.BYTE_SIZE}\n")

    properties.forEach(property => {
      val identifier = property.getName()
      val code = SnakeCaseStyle.toCase(identifier).toUpperCase
      val index = property.getIndex()

      builder.append(s"   val ${code}_ID: Byte = ${index + 1}\n")
    })
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

  private def generateMatchMethod(): Unit = {
    val properties: util.List[Property] = entity.getProperties
    val name: String = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   override def handle(handler: ${name}Handler): Boolean = {\n")
    builder.append("      val code = buffer.getByte(offset)\n")
    builder.append("\n")
    builder.append("      code match {\n")

    properties.forEach(property => {
      val identifier = property.getName()
      val code = SnakeCaseStyle.toCase(identifier).toUpperCase
      val method = PascalCaseStyle.toCase(identifier)

      builder.append(s"         case ${name}Codec.${code}_ID => {\n")
      builder.append(s"            this.${identifier}Codec.assign(this.buffer, this.offset + ${name}Codec.HEADER_SIZE, this.length - ${name}Codec.HEADER_SIZE)\n")
      builder.append(s"            handler.on${method}(this.${identifier}Codec)\n")
      builder.append(s"            true\n")
      builder.append(s"         }\n")
    })
    builder.append(s"         case _ => {\n")
    builder.append(s"            false\n")
    builder.append(s"         }\n")
    builder.append(s"      }\n")
    builder.append(s"   }\n")
  }

  private def generateDefaultsMethod(): Unit = {
    val name: String = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   override def defaults(): ${name}Codec = {\n")
    builder.append("      this.buffer.setByte(this.offset, 0)\n")
    builder.append(s"      this\n")
    builder.append(s"   }\n")
  }

  private def generateClearMethod(): Unit = {
    val name: String = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   override def clear(): ${name}Codec = {\n")
    builder.append("      this.buffer.setByte(this.offset, 0)\n")
    builder.append(s"      this\n")
    builder.append(s"   }\n")
  }

  private def generateValidationMethod(): Unit = {
    val properties: util.List[Property] = entity.getProperties
    val name: String = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   override def validate(): ResultCode = {\n")
    builder.append("      val code = buffer.getByte(offset)\n")
    builder.append("\n")
    builder.append("      code match {\n")

    properties.forEach(property => {
      val identifier = property.getName()
      val code = SnakeCaseStyle.toCase(identifier).toUpperCase

      builder.append(s"         case ${name}Codec.${code}_ID => {\n")
      builder.append(s"            this.${identifier}().validate()\n")
      builder.append(s"         }\n")
    })
    builder.append(s"         case _ => {\n")
    builder.append("            ResultCode.fail(\"Code not supported\")\n")
    builder.append(s"         }\n")
    builder.append(s"      }\n")
    builder.append(s"   }\n")
  }
}
