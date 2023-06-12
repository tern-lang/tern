package trumid.poc.dsl.tree.struct

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import trumid.poc.model.Entity

trait StructElement {
  def define(scope: Scope, entity: Entity, path: Path): Unit = {}
  def include(scope: Scope, entity: Entity, path: Path): Unit = {}
  def process(scope: Scope, entity: Entity, path: Path): Unit = {}
  def extend(scope: Scope, entity: Entity, path: Path): Unit = {}
}
