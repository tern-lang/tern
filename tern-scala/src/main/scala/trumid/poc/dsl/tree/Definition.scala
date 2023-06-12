package trumid.poc.dsl.tree

import org.ternlang.core.scope.Scope
import trumid.poc.model.SourceUnit

trait Definition[T] {
  def define(scope: Scope, unit: SourceUnit): T
  def include(scope: Scope, unit: SourceUnit): Unit = {}
  def process(scope: Scope, unit: SourceUnit): Unit = {}
  def extend(scope: Scope, unit: SourceUnit): Unit = {}
}
