package trumid.poc.cluster.codegen.service

import trumid.poc.codegen.common.Template
import trumid.poc.model.{Domain, Entity, Mode}

class ServicePublisher(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Publisher"
  override protected def getCategory() = "final class"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.poc.common.array._\n")
    builder.append("import trumid.poc.common.message._\n")
    builder.append("import trumid.poc.common.topic._\n")
  }

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    builder.append(s"${category} ${name}Publisher(consumer: MessageConsumer[${name}Codec]) {")
    generateBody()
    builder.append("}\n")
  }

  override protected def generateBody(): Unit = {
    builder.append("\n")
    generateFields()
    generatePublishMethods()
  }

  private def generateFields(): Unit = {
    val name = entity.getName(mode)

    builder.append(s"   private val composer = new TopicMessageComposer[${name}Codec](\n")
    builder.append(s"      new ${name}Codec(true),\n")
    builder.append(s"      DirectByteBuffer(),\n")
    builder.append(s"      " + ServiceTopic.generateTopicCode(entity, mode) + ",\n")
    builder.append(s"      0)\n")
  }

  private def generatePublishMethods(): Unit = {
    val name = entity.getName(mode)

    entity.getProperties().forEach(property => {
      val identifier = property.getName()
      val constraint = property.getConstraint()

      builder.append("\n")
      builder.append(s"   def ${identifier}(header: MessageHeader, builder: (${constraint}Codec) => Unit): Unit = {\n")
      builder.append(s"      val ${identifier} = this.composer.compose().${identifier}()\n")
      builder.append(s"\n")
      builder.append(s"      try {\n")
      builder.append(s"         builder.apply(${identifier})\n")
      builder.append(s"         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)\n")
      builder.append(s"      } finally {\n")
      builder.append(s"         ${identifier}.reset()\n")
      builder.append(s"      }\n")
      builder.append(s"   }\n")
    })
  }
}
