package trumid.poc.dsl.tree.constraint

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import trumid.poc.model.Property

trait Constraint {
  def include(scope: Scope, property: Property, path: Path): Unit
  def process(scope: Scope, property: Property, path: Path): Unit
}
