package trumid.poc.dsl.tree.enumeration

import org.ternlang.common.Array
import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import trumid.poc.dsl.tree.EntityDefinition
import trumid.poc.model.{Entity, EnumCategory, SourceUnit}

class EnumDefinition(annotations: AnnotationList, identifier: TextLiteral, properties: Array[EnumProperty])
    extends EntityDefinition[Entity] {

  private val reference = new NameReference(identifier)

  override def define(scope: Scope, unit: SourceUnit): Entity = {
    val name = reference.getName(scope)
    val entity = unit.addEntity(name)

    entity.setCategory(EnumCategory)

    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Enum '${name}' has no entities")
    }
    if (properties.length >= 255) {
      throw new IllegalStateException(s"Enum '${name}' is unable to support ${properties.length} values")
    }
    entity
  }

  override def include(scope: Scope, unit: SourceUnit): Unit = {
    val name = reference.getName(scope)
    val entity = unit.getEntity(name)
    val path = unit.getPath()

    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Enum '${name}' has no entities")
    }
    properties.forEach(property => {
      property.include(scope, entity, path)
    })
  }

  override def process(scope: Scope, unit: SourceUnit): Unit = {
    val name = reference.getName(scope)
    val entity = unit.getEntity(name)
    val path = unit.getPath

    if (entity == null) {
      throw new IllegalStateException(s"Enum ${name} has not been defined")
    }
    properties.forEach(property => {
      property.process(scope, entity, path)
    })
    validate(scope, entity, path)
  }
}
