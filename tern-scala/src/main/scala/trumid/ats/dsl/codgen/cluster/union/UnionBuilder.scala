package trumid.ats.dsl.codgen.cluster.union

import trumid.poc.codegen.common.Template
import trumid.poc.common.PascalCaseStyle
import trumid.poc.model.{Domain, Entity, Mode, Property}

import java.util

class UnionBuilder(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Builder"
  override protected def getCategory() = "trait"

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    builder.append(s"${category} ${getName} extends ${name} {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {
    val properties: util.List[Property] = entity.getProperties

    builder.append("\n")
    properties.forEach(property => {
      val identifier: String = property.getName()
      val constraint: String = property.getConstraint(mode)
      val method: String = PascalCaseStyle.toCase(identifier)

      builder.append(s"   override def ${identifier}(): ${constraint}Builder\n")
    })
    generateDefaultsMethod()
    generateClearMethod()
  }

  private def generateDefaultsMethod(): Unit = {
    builder.append(s"   def defaults(): ${getName}\n")
  }

  private def generateClearMethod(): Unit = {
    builder.append(s"   def clear(): ${getName}\n")
  }
}
