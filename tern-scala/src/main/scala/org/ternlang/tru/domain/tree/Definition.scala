package org.ternlang.tru.domain.tree

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.Namespace

trait Definition[T] {
  def define(scope: Scope, namespace: Namespace, path: Path): T
  def include(scope: Scope, namespace: Namespace, path: Path): Unit = {}
  def process(scope: Scope, namespace: Namespace, path: Path): Unit = {}
  def extend(scope: Scope, namespace: Namespace, path: Path): Unit = {}
}
