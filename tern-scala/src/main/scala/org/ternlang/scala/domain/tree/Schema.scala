package org.ternlang.scala.domain.tree

import org.ternlang.core.scope.Scope
import org.ternlang.scala.domain.Domain


class Schema(namespace: Namespace, imports: ImportList, definitions: Array[Definition] = Array.empty) {

  def define(scope: Scope, domain: Domain) = {
  }

  def include(scope: Scope, domain: Domain) = {

  }

  def process(scope: Scope, domain: Domain) = {

  }

  def extend(scope: Scope, domain: Domain) = {

  }
}
