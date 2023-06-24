package trumid.poc.cluster.codegen.service

import org.ternlang.core.annotation.Annotation
import trumid.poc.cluster.codegen.union._
import trumid.poc.model.{Domain, Entity, Mode}

class ServiceCodec(domain: Domain, entity: Entity, mode: Mode) extends UnionCodec(domain, entity, mode) {

  protected override def generateTopicMethod(): Unit = {
    val name: String = entity.getName(mode)

    builder.append("\n")
    builder.append(s"   override def topic(handler: ${name}Handler): TopicRoute = {\n")
    builder.append("      Topic(" + generateTopicCode() + ", \"" + name + "\").route((frame, payload) => {\n")
    builder.append("         assign(\n")
    builder.append("            payload.getBuffer,\n")
    builder.append("            payload.getOffset,\n")
    builder.append("            payload.getLength).handle(handler)\n")
    builder.append("      })\n")
    builder.append("   }\n")
  }

  private def generateTopicCode(): Byte = {
    val name: String = entity.getName(mode)
    val annotation: Annotation = entity.getAnnotations().get("Topic")

    if (annotation == null) {
      throw new IllegalStateException("Service has no @Topic annotation")
    }
    val code = annotation.getAttribute("code")
      .asInstanceOf[Number]
      .byteValue()

    if (name.endsWith("Response")) {
      ((code * 10) + 1).asInstanceOf[Byte]
    } else {
      (code * 10).asInstanceOf[Byte]
    }
  }
}
