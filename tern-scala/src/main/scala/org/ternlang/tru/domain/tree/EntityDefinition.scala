package org.ternlang.tru.domain.tree

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tru.domain.AnnotationType
import org.ternlang.tru.model.{Entity, Property}

trait EntityDefinition[T] extends Definition[T] {

  def validate(scope: Scope, entity: Entity, path: Path): Unit = {
    entity.getAnnotations().keySet().forEach((annotation: String) => {
      if (AnnotationType.resolve(annotation).isEmpty) {
        throw new IllegalStateException(s"Annotation '@${annotation}' on '${entity}' is not supported")
      }
    })
    entity.getProperties().forEach((property: Property) => {
      property.getAnnotations().keySet().forEach((annotation: String) => {
        if (AnnotationType.resolve(annotation).isEmpty) {
          throw new IllegalStateException(s"Annotation '@${annotation}' on '${property}' is not supported for '${entity}'")
        }
      })
    })
  }
}
