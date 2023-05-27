package org.ternlang.tru.domain.tree.imports

import org.ternlang.common.Array
import org.ternlang.core.scope.Scope
import org.ternlang.tru.model.{Domain, Namespace}

case class ImportList(imports: Array[Import]) {

  def define(scope: Scope, namespace: Namespace): Unit = {
    imports.forEach(entry => {
      entry.define(scope, namespace)
    })
  }

  def include(scope: Scope, namespace: Namespace): Unit = {
    imports.forEach(entry => {
      entry.include(scope, namespace)
    })
  }
}
