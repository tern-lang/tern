package org.ternlang.tru.domain.tree

import org.ternlang.common.Array
import org.ternlang.core.scope.Scope
import org.ternlang.tru.domain.tree.imports.ImportList
import org.ternlang.tru.model.{Domain, Namespace}

class Source(namespace: Package, imports: ImportList, definitions: Array[Definition[_]]) {

  def define(scope: Scope, domain: Domain): Namespace = {
    val local = namespace.define(scope, domain)

    imports.define(scope, local)
    definitions.forEach(definition => {
      definition.define(scope, local, namespace.path)
    })

    local
  }

  def include(scope: Scope, domain: Domain): Unit = {
    val local = namespace.include(scope, domain)

    local.setScope(scope)
    imports.include(scope, local)
    definitions.forEach(definition => {
      definition.include(scope, local, namespace.path)
    })
  }

  def process(scope: Scope, domain: Domain): Unit = {
    val local = namespace.process(scope, domain)
    val inner = local.getScope

    definitions.forEach(definition => {
      definition.process(inner, local, namespace.path)
    })
  }

  def extend(scope: Scope, domain: Domain): Unit = {
    val local = namespace.extend(scope, domain)
    val inner = local.getScope

    definitions.forEach(definition => {
      definition.extend(inner, local, namespace.path)
    })
  }
}
