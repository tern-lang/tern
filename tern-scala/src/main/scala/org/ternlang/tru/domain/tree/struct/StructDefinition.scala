package org.ternlang.tru.domain.tree.struct

import org.ternlang.common.Array
import org.ternlang.core.annotation.Annotation
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.domain.tree.EntityDefinition
import org.ternlang.tru.domain.tree.annotation.AnnotationProcessor
import org.ternlang.tru.model._

import java.util
import java.util.concurrent.atomic.AtomicBoolean

class StructDefinition(val annotations: AnnotationList,
                       val identifier: TextLiteral,
                       val extension: StructExtension,
                       val elements: Array[StructElement]) extends EntityDefinition[Entity] {

  final protected var processor: AnnotationProcessor = new AnnotationProcessor(annotations)
  final protected var reference: NameReference = new NameReference(identifier)
  final protected var extended: AtomicBoolean = new AtomicBoolean

  def this(annotations: AnnotationList, identifier: TextLiteral, elements: Array[StructElement]) {
    this(annotations, identifier, null, elements)
  }

  override def define(scope: Scope, namespace: Namespace, path: Path): Entity =
    define(scope, namespace, path, StructCategory)

  protected def define(scope: Scope, namespace: Namespace, path: Path, category: Category): Entity = {
    val name: String = reference.getName(scope)
    val entity: Entity = namespace.addEntity(name)
    val annotations: util.Map[String, Annotation] = entity.getAnnotations()

    entity.setCategory(category)
    processor.create(scope, annotations)

    if (extension != null) {
      val base: String = extension.evaluate(scope)
      entity.setExtends(base)
    }
    elements.forEach(element => {
      element.define(scope, entity, path)
    })
    entity
  }

  override def include(scope: Scope, module: Namespace, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = module.getEntity(name)

    elements.forEach(element => {
      element.include(scope, entity, path)
    })
  }

  override def process(scope: Scope, module: Namespace, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = module.getEntity(name)

    elements.forEach(element => {
      try {
        element.process(scope, entity, path)
      } catch {
        case e: Exception =>
          throw new IllegalStateException(s"Error creating '${name}' in ${path}", e)
      }
    })
    validate(scope, entity, path)
  }

  override def extend(scope: Scope, module: Namespace, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val entity: Entity = module.getEntity(name)
    val requires: String = entity.getExtends

    if (!extended.compareAndSet(false, true)) {
      throw new IllegalStateException(s"Extension for '${name}' already done")
    }
    if (requires != null) {
      val base: Entity = module.getVisibleEntity(requires)

      if (base == null) {
        throw new IllegalStateException(s"Could not resolve '${requires}' from '${module}'")
      }
      val baseType: Category = base.getCategory()
      val entityType: Category = entity.getCategory()

      if (entityType.isInterface && !baseType.isInterface) {
        throw new IllegalStateException(s"Interface '${name}' extends '${requires}' which is not an interface")
      }
      val properties: util.List[Property] = base.getProperties

      properties.forEach(property => {
        val identifier: String = property.getName
        val existing: Property = entity.getProperty(identifier)

        if (existing != null) {
          throw new IllegalStateException(s"Property '${identifier}' already exists for '${name}'")
        }
        entity.addProperty(identifier).copy(property)
      })
    }
    elements.forEach(element => {
      element.extend(scope, entity, path)
    })
  }
}
