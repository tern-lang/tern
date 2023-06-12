package trumid.poc.dsl.tree.struct

import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral

case class StructExtension(identifier: TextLiteral) {

  def evaluate(scope: Scope): String = new NameReference(identifier).getName(scope)
}
