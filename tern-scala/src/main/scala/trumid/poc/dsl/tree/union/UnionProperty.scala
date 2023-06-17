package trumid.poc.dsl.tree.union

import org.ternlang.core.annotation.Annotation
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import trumid.poc.common.CamelCaseStyle
import trumid.poc.dsl.tree.annotation.AnnotationProcessor
import trumid.poc.dsl.tree.struct.StructElement
import trumid.poc.model._

import java.util

class UnionProperty(annotations: AnnotationList, identifier: TextLiteral, entity: TextLiteral) extends StructElement {
  protected val processor = new AnnotationProcessor(annotations)
  protected val method = new NameReference(identifier)
  protected val constraint = new NameReference(entity)

  override def define(scope: Scope, entity: Entity, path: Path): Unit = {
    val name = CamelCaseStyle.toCase(method.getName(scope))
    val property = entity.addProperty(name)
    val annotations: util.Map[String, Annotation] = property.getAnnotations()

    processor.create(scope, annotations)
    property.getMask().add(Property.ENTITY)
    property.setConstraint(constraint.getName(scope))
    update(scope, entity, path, property)
  }

  override def process(scope: Scope, entity: Entity, path: Path): Unit = {
    val name = CamelCaseStyle.toCase(method.getName(scope))
    val property = entity.getProperty(name)

    if (property == null) {
      throw new IllegalStateException(s"No such property '${name}' for '${entity}'")
    }
    val union = entity.getName()
    val constraint = property.getConstraint()
    val resolved = entity.getNamespace().getVisibleEntity(constraint)

    if (resolved == null) {
      throw new IllegalStateException(s"No type for '${constraint}' in '${union}'")
    }
    val category = resolved.getCategory()

    if (category.isUnion()) {
      throw new IllegalStateException(s"Illegal nesting of union '${constraint}' in '${union}'")
    }
  }

  protected def update(scope: Scope, entity: Entity, path: Path, property: Property): Unit = {
  }
}
