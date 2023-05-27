package org.ternlang.tru.domain.tree

import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.Property

trait Constraint {
  def include(scope: Scope, property: Property): Unit
  def process(scope: Scope, property: Property): Unit
}

