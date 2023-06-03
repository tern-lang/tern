package org.ternlang.tru.cluster.codegen.struct

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.model.{Domain, Entity, Mode}

class StructBuilder(domain: Domain, entity: Entity, mode: Mode)  extends Template(domain, entity, mode) {

  override protected def getName(): String = s"${entity.getName(mode)}Builder"

  override protected def getCategory() = "trait"

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    builder.append(s"${category} ${name}Builder extends ${name} {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {
    builder.append("\n")
    generateDefaultsMethod()
    generateClearMethod()
  }

  private def generateDefaultsMethod(): Unit = {
    builder.append("\n")
    builder.append("   /**\n")
    builder.append("    * Set the default values.\n")
    builder.append("    * @returns fluent builder\n")
    builder.append("    */\n")
    builder.append(s"   def defaults(): ${getName}\n")
  }

  private def generateClearMethod(): Unit = {
    builder.append("\n")
    builder.append("   /**\n")
    builder.append("    * Clear the values.\n")
    builder.append("    * @returns fluent builder\n")
    builder.append("    */\n")
    builder.append(s"   def clear(): ${getName}\n")
    builder.append("\n")
  }
}
