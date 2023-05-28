package org.ternlang.tru.domain.tree.enumeration

import org.ternlang.core.Evaluation
import org.ternlang.core.scope.Scope
import org.ternlang.core.variable.Value
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.model.EnumValue
import org.ternlang.tru.text.SnakeCaseStyle

import java.util
import java.util.Collections

class EnumReference(identifier: TextLiteral, value: TextLiteral) extends Evaluation {
  private val assignment: NameReference = new NameReference(value)
  private val reference: NameReference = new NameReference(identifier)

  override def evaluate(scope: Scope, left: Value): Value = {
    val result: EnumValue = create(scope)
    val list: util.List[EnumValue] = Collections.singletonList(result)

    Value.getTransient(list)
  }

  private def create(scope: Scope): EnumValue = {
    try {
      val constraint: String = reference.getName(scope)
      val element: String = assignment.getName(scope)
      val identifier: String = SnakeCaseStyle.toCase(element)

      new EnumValue(constraint, identifier, identifier, 0)
    } catch {
      case e: Exception =>
        throw new IllegalStateException("Could not reference enum", e)
    }
  }
}
