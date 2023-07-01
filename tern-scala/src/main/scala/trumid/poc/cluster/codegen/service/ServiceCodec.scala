package trumid.poc.cluster.codegen.service

import trumid.poc.cluster.codegen.union._
import trumid.poc.common.SnakeCaseStyle
import trumid.poc.model.{Domain, Entity, Mode}

class ServiceCodec(domain: Domain, entity: Entity, mode: Mode) extends UnionCodec(domain, entity, mode) {

  protected override def generateTopicMethod(): Unit = {
    val name: String = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   override def topic(publisher: Publisher): TopicRoute = {\n")
    builder.append("      Topic(" + ServiceTopic.generateTopicCode(entity, mode) + ", \"" + name + "\").route((frame, payload) => {\n")
    builder.append("         publisher.publish(\n")
    builder.append("            frame.getFrame.getBuffer,\n")
    builder.append("            frame.getFrame.getOffset,\n")
    builder.append("            frame.getFrame.getLength)\n")
    builder.append("      })\n")
    builder.append("   }\n")
    builder.append("\n")
    builder.append(s"   override def topic(handler: ${name}Handler): TopicRoute = {\n")
    builder.append("      Topic(" + ServiceTopic.generateTopicCode(entity, mode) + ", \"" + name + "\").route((frame, payload) => {\n")
    builder.append("         assign(\n")
    builder.append("            payload.getBuffer,\n")
    builder.append("            payload.getOffset,\n")
    builder.append("            payload.getLength).handle(handler)\n")
    builder.append("      })\n")
    builder.append("   }\n")
  }

  protected override def generateConvertMethod(): Unit = {
    val topic = ServiceTopic.generateTopicCode(entity, mode)
    val name: String = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   override def complete(scheduler: CompletionScheduler): TopicCompletionHandler = {\n")
    builder.append("      Topic(" + topic + ", \"" + name + "\").complete(this, (header) => {\n")
    builder.append("         val code = buffer.getByte(offset)\n")
    builder.append("\n")
    builder.append("         code match {\n")

    entity.getProperties().forEach(property => {
      val identifier = property.getName()
      val code = SnakeCaseStyle.toCase(identifier).toUpperCase

      builder.append(s"            case ${name}Codec.${code}_ID => {\n")

      if(property.isStreams()) {
        builder.append(s"               val completion = scheduler.done(code, ${topic})\n")
      } else {
        builder.append(s"               val completion = scheduler.done(code, header.getCorrelationId)\n")
      }
      builder.append(s"               completion.complete(this.${identifier}Codec.reset().assign(this.buffer, this.offset + ${name}Codec.HEADER_SIZE, this.length - ${name}Codec.HEADER_SIZE))\n")
      builder.append(s"            }\n")
    })
    builder.append(s"            case _ => {\n")
    builder.append("               val completion = scheduler.done(code, header.getCorrelationId)\n")
    builder.append("               completion.failure(new IllegalStateException(\"Invalid code \" + code))\n")
    builder.append(s"            }\n")
    builder.append("         }\n")
    builder.append("      })\n")
    builder.append("   }\n")
  }
}

