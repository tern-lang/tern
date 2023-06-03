package org.ternlang.tru.domain.tree

import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.SourceUnit

trait Definition[T] {
  def define(scope: Scope, unit: SourceUnit): T
  def include(scope: Scope, unit: SourceUnit): Unit = {}
  def process(scope: Scope, unit: SourceUnit): Unit = {}
  def extend(scope: Scope, unit: SourceUnit): Unit = {}
}
