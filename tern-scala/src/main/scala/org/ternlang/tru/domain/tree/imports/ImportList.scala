package org.ternlang.tru.domain.tree.imports

import org.ternlang.common.Array
import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.{Domain, Namespace, SourceUnit}

case class ImportList(imports: Array[Import]) {

  def define(scope: Scope, unit: SourceUnit): Unit = {
    imports.forEach(entry => {
      entry.define(scope, unit)
    })
  }

  def include(scope: Scope, unit: SourceUnit): Unit = {
    imports.forEach(entry => {
      entry.include(scope, unit)
    })
  }
}
