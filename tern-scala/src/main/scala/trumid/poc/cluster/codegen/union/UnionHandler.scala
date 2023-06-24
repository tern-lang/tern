package trumid.poc.cluster.codegen.union

import trumid.poc.codegen.common.Template
import trumid.poc.common.PascalCaseStyle
import trumid.poc.model.{Domain, Entity, Mode, Property}

import java.util

class UnionHandler(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Handler"
  override protected def getCategory() = "trait"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.poc.common.array._\n")
    builder.append("import trumid.poc.cluster.ResultCode\n")
  }

  override protected def generateEntity(): Unit = {
    val category: String = getCategory()
    val name: String = entity.getName(mode)

    builder.append(s"${category} ${name}Handler {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {
    val properties: util.List[Property] = entity.getProperties

    builder.append("\n")
    properties.forEach(property => {
      val identifier: String = property.getName
      val constraint: String = property.getConstraint(mode)
      val method: String = PascalCaseStyle.toCase(identifier)

      builder.append(s"   def on${method}(${identifier}: ${constraint}): Unit\n")
    })
  }
}
