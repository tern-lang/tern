package trumid.poc.cluster.codegen.service

import trumid.poc.codegen.common.Template
import trumid.poc.common.SnakeCaseStyle
import trumid.poc.model.{Domain, Entity, Mode}

class ServiceClient(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "Client"
  override protected def getCategory() = "final class"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.poc.common.array._\n")
    builder.append("import trumid.poc.common.message._\n")
    builder.append("import trumid.poc.common.topic._\n")
    builder.append("import java.util.concurrent.atomic._\n")
    builder.append("import scala.concurrent._\n")
  }

  override protected def generateEntity(): Unit = {
    val category = getCategory()
    val name = entity.getName(mode)

    builder.append(s"${category} ${name}Client(consumer: MessageConsumer[${name}Codec], scheduler: CompletionScheduler) {")
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

    builder.append(s"   private val counter = new AtomicLong(1)\n")
    builder.append(s"   private val composer = new TopicMessageComposer[${name}Codec](\n")
    builder.append(s"      new ${name}Codec(true),\n")
    builder.append(s"      DirectByteBuffer(),\n")
    builder.append(s"      " + ServiceTopic.generateTopicCode(entity, mode) + ",\n")
    builder.append(s"      0)\n")
  }

  private def generatePublishMethods(): Unit = {
    val name = entity.getName(mode)
    val response = entity.getNamespace().getVisibleEntity(name + "Response")

    if(response != null) {
      val responseCode = ServiceTopic.generateTopicCode(response, mode)

      entity.getProperties().forEach(property => {
        val response = property.getResponse()

        if (response != null) {
          val identifier = property.getName()
          val constraint = property.getConstraint()
          val responseId = s"${name}ResponseCodec.${SnakeCaseStyle.toCase(identifier).toUpperCase}_RESPONSE_ID"

          builder.append("\n")

          if (property.isStreams()) {
            builder.append(s"   def ${identifier}(builder: (${constraint}Builder) => Unit): Stream[${response}] = {\n")
          } else {
            builder.append(s"   def ${identifier}(builder: (${constraint}Builder) => Unit): Call[${response}] = {\n")
          }
          if (property.isStreams()) {
            builder.append(s"      val correlationId = ${responseCode}\n")
            builder.append(s"      this.scheduler.stream(${responseId}, correlationId, 500000, completion => {\n")
          } else {
            builder.append(s"      val correlationId = (this.counter.getAndIncrement() << 8) | ${responseCode}\n")
            builder.append(s"      this.scheduler.call(${responseId}, correlationId, 5000, completion => {\n")
          }
          builder.append(s"         val ${identifier} = this.composer.compose().${identifier}()\n")
          builder.append(s"\n")
          builder.append(s"         try {\n")
          builder.append(s"            builder.apply(${identifier})\n")
          builder.append(s"            this.composer.commit(this.consumer, 1, correlationId)\n")
          builder.append(s"         } catch {\n")
          builder.append(s"            case cause: Throwable => {\n")
          builder.append(s"               completion.failure(cause)\n")
          builder.append(s"            }\n")
          builder.append(s"         } finally {\n")
          builder.append(s"            ${identifier}.reset()\n")
          builder.append(s"         }\n")
          builder.append(s"      })\n")
          builder.append(s"   }\n")
        }
      })
    }
  }
}
