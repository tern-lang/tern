package org.ternlang.tru.dsl.tree.struct

import org.ternlang.core.Evaluation
import org.ternlang.core.annotation.Annotation
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.core.variable.Value
import org.ternlang.tree.NameReference
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.dsl.tree.annotation.AnnotationProcessor
import org.ternlang.tru.dsl.tree.constraint.Constraint
import org.ternlang.tru.model.{Entity, Property}

import java.util
import java.util.Collections

class StructProperty(val annotations: AnnotationList,
                     val identifier: TextLiteral,
                     val constraint: Constraint,
                     val assignment: Evaluation) extends StructElement {

  val RESERVED: util.Set[String] = Collections.unmodifiableSet(new util.LinkedHashSet[String](
    util.Arrays.asList("validate", "defaults", "clear")))

  protected val processor: AnnotationProcessor = new AnnotationProcessor(annotations)
  protected val reference: NameReference = new NameReference(identifier)

  def this(annotations: AnnotationList, identifier: TextLiteral, constraint: Constraint) {
    this(annotations, identifier, constraint, null)
  }

  override def include(scope: Scope, entity: Entity, path: Path): Unit = {
    val name: String = reference.getName(scope)

    if (RESERVED.contains(name)) {
      throw new IllegalStateException(s"Property ${name} is reserved")
    }
    val property: Property = entity.addProperty(name)
    val annotations: util.Map[String, Annotation] = property.getAnnotations

    if (assignment != null) {
      val value: Value = assignment.evaluate(scope, null)
      val data: Any = value.getValue

      if (data != null) {
        val token: String = String.valueOf(data)
        val constant: Value = scope.getState.getValue(token)
        val result: Any = if (constant != null) {
          constant.getValue
        } else {
          token
        }
        if (result == null) {
          throw new IllegalStateException(s"Could not resolve assignment for ${property}")
        }
        property.setDefaultValue(String.valueOf(result))
      }
    }
    processor.create(scope, annotations)
    constraint.include(scope, property, path)
  }

  override def process(scope: Scope, entity: Entity, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val property: Property = entity.getProperty(name)
    val parent: String = entity.getName

    if (property == null) {
      throw new IllegalStateException(s"No such property '${name}' for '${parent}'")
    }
    try {
      constraint.process(scope, property, path)
    } catch {
      case e: Exception =>
        throw new IllegalStateException(s"Invalid property '${name}' for '${parent}'", e)
    }
  }
}
