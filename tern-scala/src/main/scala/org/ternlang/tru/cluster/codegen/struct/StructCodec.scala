package org.ternlang.tru.cluster.codegen.struct

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.model.{Domain, Entity, Mode}

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
    builder.append("import org.ternlang.tru.common._\n")
    builder.append("import org.ternlang.tru.common.message._\n")
  }

  override protected def generateBody(): Unit = {
    builder.append("\n")
    builder.append("   private var buffer: ByteBuffer = _\n")
    builder.append("   private var offset: Int = _\n")
    builder.append("   private var length: Int = _\n")

    generateAssignMethod()
    generateValidationMethod()
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
    builder.append("      this;\n")
    builder.append("   }\n")
  }

  private def generateValidationMethod(): Unit = {
    builder.append("\n")
    builder.append(s"   def validate(): ResultCode = {\n")
    builder.append(s"      ${entity.getName(mode)}Validator.validate(this)\n")
    builder.append(s"   }\n")
  }
}
