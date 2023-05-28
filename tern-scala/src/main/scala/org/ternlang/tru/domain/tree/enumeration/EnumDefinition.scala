package org.ternlang.tru.domain.tree.enumeration

import org.ternlang.common.Array
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.domain.tree.EntityDefinition
import org.ternlang.tru.model.{Entity, EnumCategory, Namespace}

class EnumDefinition(annotations: AnnotationList, identifier: TextLiteral, properties: Array[EnumProperty])
    extends EntityDefinition[Entity] {

  private val reference = new NameReference(identifier)

  override def define(scope: Scope, namespace: Namespace, path: Path): Entity = {
    val name = reference.getName(scope)
    val entity = namespace.addEntity(name)

    entity.setCategory(EnumCategory)

    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Enum '${name}' has no entities")
    }
    if (properties.length >= 255) {
      throw new IllegalStateException(s"Enum '${name}' is unable to support ${properties.length} values")
    }
    entity
  }

  override def include(scope: Scope, namespace: Namespace, path: Path): Unit = {
    val name = reference.getName(scope)
    val entity = namespace.getEntity(name)

    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Enum '${name}' has no entities")
    }
    properties.forEach(property => {
      property.include(scope, entity, path)
    })
  }

  override def process(scope: Scope, namespace: Namespace, path: Path): Unit = {
    val name = reference.getName(scope)
    val entity = namespace.getEntity(name)

    if (entity == null) {
      throw new IllegalStateException(s"Enum ${name} has not been defined")
    }
    properties.forEach(property => {
      property.process(scope, entity, path)
    })
    validate(scope, entity, path)
  }
}
