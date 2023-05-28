package org.ternlang.tru.domain.tree

import org.ternlang.core.module.Path
import org.ternlang.core.scope.{Scope, ScopeState}
import org.ternlang.core.variable.Value
import org.ternlang.tru.domain.tree.imports.ResourcePath
import org.ternlang.tru.model.{Domain, Namespace}

class Package(val qualifier: String, val path: Path) {

  def define(scope: Scope, domain: Domain): Namespace = {
    try {
      domain.addNamespace(qualifier).addImport(ResourcePath.resolve(path))
    } catch {
      case e: Exception =>
        throw new IllegalStateException(s"Namespace '${qualifier}' could not be created from '${path.getPath}'", e)
    }
  }

  def include(scope: Scope, domain: Domain): Namespace = {
    val namespace: Namespace = domain.getNamespace(qualifier)
    val state: ScopeState = scope.getState

    namespace.getEntities().forEach(entity => {
      val name: String = entity.getName
      val value: Value = Value.getTransient(entity)

      state.addValue(name, value) // use for static analysis
    })

    namespace.getAliases().forEach(alias => {
      val name: String = alias.getName
      val value: Value = Value.getTransient(alias)
      val existing: Value = state.getValue(name)

      if (existing == null) {
        state.addValue(name, value)
      } else {
        if (existing.getValue != alias) {
          throw new IllegalStateException(s"Entity '${name}' already exists")
        }
      }
    })
    namespace
  }

  def process(scope: Scope, domain: Domain): Namespace = {
    domain.getNamespace(qualifier)
  }

  def extend(scope: Scope, domain: Domain): Namespace = {
    domain.getNamespace(qualifier)
  }
}
