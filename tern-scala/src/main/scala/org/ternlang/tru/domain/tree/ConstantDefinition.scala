package org.ternlang.tru.domain.tree

import org.ternlang.core.Evaluation
import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.Namespace
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral

class ConstantDefinition(identifier: TextLiteral, constant: Evaluation) extends Definition[AnyRef] {

  override def define(scope: Scope, namespace: Namespace): AnyRef = {
    val name = new NameReference(identifier).getName(scope)
    val value = constant.evaluate(scope, null)
    val handle = namespace.addConstant(name, value.getValue)
    val state = scope.getState

    state.addValue(name, handle)
    handle.getValue
  }
}
