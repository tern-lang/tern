package org.ternlang.tru.domain.tree.constraint

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.Property

trait Constraint {
  def include(scope: Scope, property: Property, path: Path): Unit
  def process(scope: Scope, property: Property, path: Path): Unit
}
