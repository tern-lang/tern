package org.ternlang.tru.domain.tree

import org.ternlang.common.Array
import org.ternlang.core.scope.Scope
import org.ternlang.tru.domain.tree.imports.ImportList
import org.ternlang.tru.model.{Domain, SourceUnit}

class Source(file: SourceFile, imports: ImportList, definitions: Array[Definition[_]]) {

  def define(scope: Scope, domain: Domain): SourceUnit = {
    val unit = file.define(scope, domain)

    imports.define(scope, unit)
    definitions.forEach(definition => {
      definition.define(scope, unit)
    })

    unit
  }

  def include(scope: Scope, domain: Domain): Unit = {
    val unit = file.include(scope, domain)
    val namespace = unit.getNamespace

    namespace.setScope(scope)
    imports.include(scope, unit)
    definitions.forEach(definition => {
      definition.include(scope, unit)
    })
  }

  def process(scope: Scope, domain: Domain): Unit = {
    val unit = file.include(scope, domain)
    val namespace = unit.getNamespace
    val inner = namespace.getScope

    definitions.forEach(definition => {
      definition.process(inner, unit)
    })
  }

  def extend(scope: Scope, domain: Domain): Unit = {
    val unit = file.extend(scope, domain)
    val namespace = unit.getNamespace
    val inner = namespace.getScope

    definitions.forEach(definition => {
      definition.extend(inner, unit)
    })
  }
}
