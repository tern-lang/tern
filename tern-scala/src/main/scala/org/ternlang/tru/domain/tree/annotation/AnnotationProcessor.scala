package org.ternlang.tru.domain.tree.annotation

import org.ternlang.core.annotation.{Annotated, Annotation}
import org.ternlang.core.scope.Scope
import org.ternlang.tree.annotation.AnnotationList

import java.util

object AnnotationProcessor {
  private class AnnotationCollector() extends Annotated {
    final private val annotations = new util.ArrayList[Annotation]

    override def getAnnotations: util.List[Annotation] = annotations

    def clear(): Unit = {
      annotations.clear()
    }
  }
}

class AnnotationProcessor(val annotations: AnnotationList) {
  final private val collector = new AnnotationProcessor.AnnotationCollector

  def create(scope: Scope, map: util.Map[String, Annotation]): Unit = {
    try {
      collector.clear()
      annotations.apply(scope, collector)
      collector.getAnnotations.stream.forEach((annotation: Annotation) => {
        val name = annotation.getName
        map.put(name, annotation)
      })
    } catch {
      case e: Exception =>
        throw new IllegalStateException("Could not process annotations", e)
    }
  }
}
