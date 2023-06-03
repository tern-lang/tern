package org.ternlang.tru.dsl.tree.constraint

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.parse.StringToken
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.dsl.Constants
import org.ternlang.tru.model.Property

class EntityConstraint(identifier: TextLiteral) extends Constraint {

  private val CHAR_TOKEN = new StringToken(Constants.CHAR)
  private val CHAR_LITERAL = new TextLiteral(CHAR_TOKEN)

  private val reference = new NameReference(identifier)

  override def include(scope: Scope, property: Property, path: Path): Unit = {
    define(scope, property, path)
  }

  @throws[Exception]
  private def define(scope: Scope, property: Property, path: Path): Unit = {
    val constraint = reference.getName(scope)
    property.setConstraint(constraint)
  }

  @throws[Exception]
  override def process(scope: Scope, property: Property, path: Path): Unit = {

  }

}
