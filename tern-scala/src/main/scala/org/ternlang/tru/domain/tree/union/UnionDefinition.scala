package org.ternlang.tru.domain.tree.union

import org.ternlang.common.Array
import org.ternlang.core.annotation.Annotation
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.domain.tree.EntityDefinition
import org.ternlang.tru.domain.tree.annotation.AnnotationProcessor
import org.ternlang.tru.domain.tree.struct.StructElement
import org.ternlang.tru.model.{Entity, Namespace, Property, UnionCategory}

import java.util

class UnionDefinition(val annotations: AnnotationList,
                      val identifier: TextLiteral,
                      val requirement: UnionRequirement,
                      val properties: Array[StructElement]) extends EntityDefinition[Entity] {

  private var processor: AnnotationProcessor = new AnnotationProcessor(annotations)
  private var reference: NameReference = new NameReference(identifier)

  def this(annotations: AnnotationList, identifier: TextLiteral, properties: Array[StructElement]) {
    this(annotations, identifier, null, properties)
  }

  override def define(scope: Scope, namespace: Namespace, path: Path): Entity = {
    val name: String = reference.getName(scope)
    val entity: Entity = namespace.addEntity(name)
    val annotations: util.Map[String, Annotation] = entity.getAnnotations

    entity.setCategory(UnionCategory)
    processor.create(scope, annotations)

    if (requirement != null) {
      entity.setExtends(requirement.evaluate(scope))
    }
    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Union '${name}' has no entities")
    }
    if (properties.length >= 255) {
      throw new IllegalStateException(s"Union '${name}' is unable to support ${properties.length} options")
    }
    entity
  }

  @throws[Exception]
  override def include(scope: Scope, namespace: Namespace, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = namespace.getEntity(name)

    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Union '${name}' has no entities")
    }
    properties.forEach(property => {
      property.define(scope, entity, path)
    })
    properties.forEach(property => {
      property.include(scope, entity, path)
    })
  }

  override def process(scope: Scope, namespace: Namespace, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = namespace.getEntity(name)

    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Union '${name}' has no entities")
    }
    properties.forEach(property => {
      property.process(scope, entity, path)
    })
    validate(scope, entity, path)
  }

  @throws[Exception]
  override def extend(scope: Scope, namespace: Namespace, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = namespace.getEntity(name)
    val requires: String = entity.getExtends

    properties.forEach(property => {
      property.extend(scope, entity, path)
    })
    if (requires != null) {
        val base: Entity = namespace.getVisibleEntity(requires)

        if (base == null) {
          throw new IllegalStateException(s"Could not resolve '${requires}' from '${namespace}'")
        }
        val properties: util.List[Property] = entity.getProperties
        val iterator = properties.iterator()

        while(iterator.hasNext) {
          val property = iterator.next
          val constraint: String = property.getConstraint
          var reference: Entity = namespace.getVisibleEntity(constraint)

          if (reference == null) {
            throw new IllegalStateException("Union references unknown type '" + constraint + "'")
          }
          var found: Boolean = false

          while (reference != null && found == false) {
            val actual: String = reference.getExtends

            if (requires == actual) {
              found = true
            } else if (actual == null) {
              found = true
            } else {
              reference = namespace.getEntity(actual)
            }
          }
          if (found == false) {
            throw new IllegalStateException(s"Union type '${constraint}' must extend '${requires}'")
          }
        }
    }
  }
}

