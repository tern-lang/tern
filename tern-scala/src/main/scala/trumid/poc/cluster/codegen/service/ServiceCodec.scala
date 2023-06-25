package trumid.poc.cluster.codegen.service

import org.ternlang.core.annotation.Annotation
import trumid.poc.cluster.codegen.union._
import trumid.poc.common.{PascalCaseStyle, SnakeCaseStyle}
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
    val name: String = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   override def complete(scheduler: CompletionScheduler): TopicCompletionHandler = {\n")
    builder.append("      Topic(" + ServiceTopic.generateTopicCode(entity, mode) + ", \"" + name + "\").complete(this, (header) => {\n")
    builder.append("         val correlationId = header.getCorrelationId\n")
    builder.append("         val completion = scheduler.stop(correlationId)\n")
    builder.append("\n")
    builder.append("         if(completion != null) {\n")
    builder.append("            val code = buffer.getByte(offset)\n")
    builder.append("\n")
    builder.append("            code match {\n")

    entity.getProperties().forEach(property => {
      val identifier = property.getName()
      val code = SnakeCaseStyle.toCase(identifier).toUpperCase
      val method = PascalCaseStyle.toCase(identifier)

      builder.append(s"               case ${name}Codec.${code}_ID => {\n")
      builder.append(s"                  completion.complete(this.${identifier}Codec.assign(this.buffer, this.offset + ${name}Codec.HEADER_SIZE, this.length - ${name}Codec.HEADER_SIZE))\n")
      builder.append(s"               }\n")
    })
    builder.append(s"               case _ => {\n")
    builder.append("                  completion.failure(new IllegalStateException(\"Invalid code \" + code))\n")
    builder.append(s"               }\n")
    builder.append("            }\n")
    builder.append("         }\n")
    builder.append("      })\n")
    builder.append("   }\n")
  }
}

