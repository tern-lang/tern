package org.ternlang.tru.domain.tree

import org.ternlang.common.Array
import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.Domain

case class ImportList(imports: Array[Import]) {

  def define(scope: Scope, domain: Domain): Unit = {
    imports.forEach(entry => {
      entry.define(scope, domain)
    })
  }

  def include(scope: Scope, domain: Domain): Unit = {
    imports.forEach(entry => {
      entry.include(scope, domain)
    })
  }
}
