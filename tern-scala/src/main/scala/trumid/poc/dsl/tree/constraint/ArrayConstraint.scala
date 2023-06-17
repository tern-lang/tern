package trumid.poc.dsl.tree.constraint

import org.ternlang.core.Evaluation
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.parse.NumberToken
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.{NumberLiteral, TextLiteral}
import trumid.poc.common.Primitive
import trumid.poc.dsl.tree.Optional
import trumid.poc.dsl.tree.alias.AliasReference
import trumid.poc.model._

object ArrayConstraint {
  private val INVALID_SIZE = new NumberToken(-1)
  private val INVALID_DIMENSION = new NumberLiteral(INVALID_SIZE)
}

class ArrayConstraint(constraint: TextLiteral, dimension: Evaluation, optional: Optional) extends Constraint {
  private val alias = new AliasReference(constraint, optional)
  private val reference = new NameReference(constraint)

  def this(identifier: TextLiteral) = {
    this(identifier, ArrayConstraint.INVALID_DIMENSION, null)
  }

  def this(identifier: TextLiteral, dimension: Evaluation) = {
    this(identifier, dimension, null)
  }

  def this(identifier: TextLiteral, optional: Optional) = {
    this(identifier, ArrayConstraint.INVALID_DIMENSION, optional)
  }

  override def include(scope: Scope, property: Property, path: Path): Unit = {
    define(scope, property, path)
    alias.resolve(scope, property)
  }

  private def define(scope: Scope, property: Property, path: Path): Unit = {
    val constraint = reference.getName(scope)
    val value = dimension.evaluate(scope, null)
    val primitive = Primitive.resolve(constraint)
    val token = String.valueOf(value.getValue.asInstanceOf[Any])
    val constant = scope.getState.getValue(token) // dimension may be a constant
    val length = if (constant != null) constant.getInteger else value.getInteger

    property.setDimension(length)
    property.setConstraint(constraint)

    if (primitive != null) {
      property.getMask()
        .add(if (length == -1) Property.DYNAMIC else Property.BLANK)
        .add(if (primitive.contains(Primitive.STRING)) Property.STRING else Property.BLANK)
        .add(if (optional != null) Property.OPTIONAL else Property.BLANK)
        .add(Property.ARRAY)
        .add(Property.PRIMITIVE)
    } else {
      property.getMask()
        .add(if (length == -1) Property.DYNAMIC else Property.BLANK)
        .add(if (optional != null) Property.OPTIONAL else Property.BLANK)
        .add(Property.ARRAY)
        .add(Property.ENTITY)
    }
  }

  override def process(scope: Scope, property: Property, path: Path): Unit = {
    val mask = property.getMask()

    if (Property.isEntity(mask) || Property.isEnum(mask)) {
      val constraint = property.getConstraint()
      val state = scope.getState()
      val value = state.getValue(constraint)

      if (value != null) {
        val resolved = value.getValue()

        if (resolved.isInstanceOf[Alias]) {
          throw new IllegalStateException(s"Unresolved alias '${constraint}'")
        }
      } else {
        throw new IllegalStateException(s"Unknown constraint '${constraint}'")
      }
    }
  }
}
