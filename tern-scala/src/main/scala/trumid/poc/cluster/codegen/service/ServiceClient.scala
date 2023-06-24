package trumid.poc.cluster.codegen.service

import trumid.poc.codegen.common.Template
import trumid.poc.model.{Domain, Entity, Mode}

class ServiceClient(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Client"
  override protected def getCategory() = "final class"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.poc.common.array._\n")
    builder.append("import trumid.poc.common.message._\n")
    builder.append("import trumid.poc.common.topic._\n")
  }

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    builder.append(s"${category} ${name}Client(consumer: MessageConsumer[${name}Codec]) {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {
    builder.append("\n")
    generateFields()
    generatePublishMethod()
  }

  private def generateFields(): Unit = {
    val name = entity.getName(mode)

    builder.append(s"   private val composer = new TopicMessageComposer[${name}Codec](\n")
    builder.append(s"      new ${name}Codec(true),\n")
    builder.append(s"      DirectByteBuffer(),\n")
    builder.append(s"      0,\n")
    builder.append(s"      0\n")
    builder.append(s"   )\n")
  }

  private def generatePublishMethod(): Unit = {
    val name = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   def publish(correlationId: Long, builder: (${name}Codec) => Unit): Unit = {\n")
    builder.append(s"      builder.apply(this.composer.compose())\n")
    builder.append(s"      this.composer.commit(this.consumer, 1, correlationId)\n")
    builder.append(s"   }\n")
  }
}
