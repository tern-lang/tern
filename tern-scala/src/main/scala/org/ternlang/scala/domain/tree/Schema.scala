package org.ternlang.scala.domain.tree

import org.ternlang.common.functional.Spread
import org.ternlang.core.scope.Scope
import org.ternlang.scala.domain.Domain

class Schema(namespace: Namespace, imports: ImportList, definitions: Spread[Definition]) {

  def define(scope: Scope, domain: Domain) = {

  }

  def include(scope: Scope, domain: Domain) = {
    println(namespace)
    println(imports)
    println(definitions)
  }

  def process(scope: Scope, domain: Domain) = {

  }

  def extend(scope: Scope, domain: Domain) = {

  }
}
