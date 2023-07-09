package trumid.ats.dsl.codgen.cluster.service

import org.ternlang.core.annotation.Annotation
import trumid.poc.model._

object ServiceTopic {

  def generateTopicCode(entity: Entity, mode: Mode): Byte = {
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