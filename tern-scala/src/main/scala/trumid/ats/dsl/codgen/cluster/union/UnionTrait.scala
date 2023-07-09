package trumid.ats.dsl.codgen.cluster.union

import trumid.poc.codegen.common.Template
import trumid.poc.model.{Domain, Entity, Mode, Property}
import trumid.poc.common._
import java.util

class UnionTrait(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode)
  override protected def getCategory() = "trait"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.ats.cluster.common.array._\n")
    builder.append("import trumid.ats.cluster.common.topic._\n")
    builder.append("import trumid.ats.cluster.common.message._\n")
    builder.append("import trumid.ats.cluster.common.ResultCode\n")
  }

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    builder.append(s"${category} ${name} {")
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

      builder.append(s"   def ${identifier}(): ${constraint}\n")
      builder.append(s"   def is${method}(): Boolean\n")
    })
    generateTopicMethod()
    generateConvertMethod()
    generateMatchMethod()
    generateValidateMethod()
  }

  protected def generateTopicMethod(): Unit = {
  }

  protected def generateConvertMethod(): Unit = {
  }

  private def generateMatchMethod(): Unit = {
    builder.append(s"   def handle(handler: ${getName}Handler): Boolean\n")
  }

  private def generateValidateMethod(): Unit = {
    builder.append(s"   def validate(): ResultCode\n")
  }
}
