package trumid.poc.dsl.tree.constraint

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.parse.StringToken
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral
import trumid.poc.common.Primitive
import trumid.poc.dsl.Constants
import trumid.poc.dsl.tree.Optional
import trumid.poc.model.{Property, _}

class EntityConstraint(identifier: TextLiteral, optional: Optional) extends Constraint {

  private val CHAR_TOKEN = new StringToken(Constants.CHAR)
  private val CHAR_LITERAL = new TextLiteral(CHAR_TOKEN)

  private val string = new ArrayConstraint(CHAR_LITERAL, optional) // String -> char[]
  private val reference = new NameReference(identifier)

  def this(identifier: TextLiteral) = {
    this(identifier, null)
  }

  override def include(scope: Scope, property: Property, path: Path): Unit = {
    define(scope, property, path)
  }

  private def define(scope: Scope, property: Property, path: Path): Unit = {
    val constraint = reference.getName(scope)

    if (!Primitive.STRING.name().equals(constraint)) {
      val primitive = Primitive.resolve(constraint)

      property.getMask()
        .add(if (primitive.isDefined) Property.PRIMITIVE else Property.ENTITY)
        .add(if (optional == null) Property.OPTIONAL else Property.BLANK)

      property.setConstraint(constraint)
    } else {
      string.include(scope, property, path)
    }
  }

  override def process(scope: Scope, property: Property, path: Path): Unit = {
    val mask = property.getMask()

    if (Property.isEntity(mask) || Property.isEnum(mask)) {
      val constraint = property.getConstraint()
      val state = scope.getState()
      val value = state.getValue(constraint)

      if (value != null) {
        val resolved: Any = value.getValue()

        if (resolved.isInstanceOf[Alias]) {
          throw new IllegalStateException(s"Unresolved alias '${constraint}'")
        }
      } else {
        throw new IllegalStateException(s"Unknown constraint '${constraint}'")
      }
    }
  }
}
