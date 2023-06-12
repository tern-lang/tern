package trumid.poc.dsl.tree.union

import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral

class UnionRequirement(identifier: TextLiteral) {
  private val reference = new NameReference(identifier)
  def evaluate(scope: Scope): String = reference.getName(scope)
}
