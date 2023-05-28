package org.ternlang.tru.domain.tree.struct

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.Entity

trait StructElement {
  def define(scope: Scope, entity: Entity, path: Path): Unit = {}
  def include(scope: Scope, entity: Entity, path: Path): Unit = {}
  def process(scope: Scope, entity: Entity, path: Path): Unit = {}
  def extend(scope: Scope, entity: Entity, path: Path): Unit = {}
}

