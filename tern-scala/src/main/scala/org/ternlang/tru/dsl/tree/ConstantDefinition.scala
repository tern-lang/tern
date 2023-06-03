package org.ternlang.tru.dsl.tree

import org.ternlang.core.Evaluation
import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.model.SourceUnit

class ConstantDefinition(identifier: TextLiteral, constant: Evaluation) extends Definition[AnyRef] {

  override def define(scope: Scope, unit: SourceUnit): AnyRef = {
    val name = new NameReference(identifier).getName(scope)
    val value = constant.evaluate(scope, null)
    val handle = unit.addConstant(name, value.getValue)
    val state = scope.getState

    state.addValue(name, handle)
    handle.getValue
  }
}
