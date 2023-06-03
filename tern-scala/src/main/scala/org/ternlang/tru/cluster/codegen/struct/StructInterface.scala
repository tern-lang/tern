package org.ternlang.tru.cluster.codegen.struct

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.model.{Domain, Entity, Mode}

class StructInterface(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode)

  override protected def getCategory() = "trait"

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

  override protected def generateExtraImports(): Unit = {
    builder.append("import com.eimex.core.validate.*;\n")
  }

  override protected def generateBody(): Unit = {
    generateGetterMethods()
    generateValidationMethod()
  }

  private def generateGetterMethods(): Unit = {
    builder.append("\n")
  }

  private def generateValidationMethod(): Unit = {
    builder.append("\n")
    builder.append("   /**\n")
    builder.append("    * Validate all attributes.\n")
    builder.append("    * @returns result of validation\n")
    builder.append("    */\n")
    builder.append("   ResultCode validate();\n")
  }
}
