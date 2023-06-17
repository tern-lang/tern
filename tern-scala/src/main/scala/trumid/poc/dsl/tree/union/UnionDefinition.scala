package trumid.poc.dsl.tree.union

import org.ternlang.common.Array
import org.ternlang.core.annotation.Annotation
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import trumid.poc.model._
import trumid.poc.dsl.tree.EntityDefinition
import trumid.poc.dsl.tree.annotation.AnnotationProcessor
import trumid.poc.dsl.tree.struct.StructElement
import trumid.poc.model.{Entity, Namespace, Property, SourceUnit, UnionCategory}

import java.util

class UnionDefinition(val annotations: AnnotationList,
                      val identifier: TextLiteral,
                      val requirement: UnionRequirement,
                      val properties: Array[StructElement]) extends EntityDefinition[Entity] {

  private val processor: AnnotationProcessor = new AnnotationProcessor(annotations)
  private val reference: NameReference = new NameReference(identifier)

  def this(annotations: AnnotationList, identifier: TextLiteral, properties: Array[StructElement]) {
    this(annotations, identifier, null, properties)
  }

  override def define(scope: Scope, unit: SourceUnit): Entity = {
    val name: String = reference.getName(scope)
    val entity: Entity = unit.addEntity(name)
    val annotations: util.Map[String, Annotation] = entity.getAnnotations
    val path: Path = unit.getPath()

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
    properties.forEach(property => {
      property.define(scope, entity, path)
    })
    update(scope, unit, entity)
    entity
  }

  override def include(scope: Scope, unit: SourceUnit): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = unit.getEntity(name)
    val path: Path = unit.getPath()

    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Union '${name}' has no entities")
    }
    properties.forEach(property => {
      property.include(scope, entity, path)
    })
  }

  override def process(scope: Scope, unit: SourceUnit): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = unit.getEntity(name)
    val path: Path = unit.getPath

    if (properties == null || properties.length == 0) {
      throw new IllegalStateException(s"Union '${name}' has no entities")
    }
    properties.forEach(property => {
      property.process(scope, entity, path)
    })
    validate(scope, entity, path)
  }

  override def extend(scope: Scope, unit: SourceUnit): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = unit.getEntity(name)
    val path: Path = unit.getPath
    val requires: String = entity.getExtends

    properties.forEach(property => {
      property.extend(scope, entity, path)
    })
    if (requires != null) {
      val namespace: Namespace = unit.getNamespace()
      val base: Entity = namespace.getVisibleEntity(requires)

      if (base == null) {
        throw new IllegalStateException(s"Could not resolve '${requires}' from '${namespace}'")
      }
      val properties: util.List[Property] = entity.getProperties
      val iterator = properties.iterator()

      while (iterator.hasNext) {
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

  protected def update(scope: Scope, unit: SourceUnit, entity: Entity): Unit = {
  }
}

