package org.ternlang.tru.domain.tree

import org.ternlang.core.scope.Scope
import org.ternlang.tru.domain.AnnotationType
import org.ternlang.tru.model.{Entity, Property}

trait EntityDefinition[T] extends Definition[T] {

  def validate(scope: Scope, entity: Entity): Unit = {
    entity.getAnnotations().keySet.foreach((annotation: String) => {
      if (AnnotationType.resolve(annotation).isEmpty) {
        throw new IllegalStateException(s"Annotation '@${annotation}' on '${entity}' is not supported")
      }
    })
    entity.getProperties().foreach((property: Property) => {
      property.getAnnotations().keySet.foreach((annotation: String) => {
        if (AnnotationType.resolve(annotation).isEmpty) {
          throw new IllegalStateException(s"Annotation '@${annotation}' on '${property}' is not supported for '${entity}'")
        }
      })
    })
  }
}
