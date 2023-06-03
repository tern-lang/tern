package org.ternlang.tru.domain.tree

import org.ternlang.core.module.Path
import org.ternlang.core.scope.{Scope, ScopeState}
import org.ternlang.core.variable.Value
import org.ternlang.tru.domain.tree.imports.ResourcePath
import org.ternlang.tru.model.{Domain, SourceUnit}

class SourceFile(val qualifier: String, val path: Path) {

  def define(scope: Scope, domain: Domain): SourceUnit = {
    try {
      val unit = domain.addSourceUnit(path.getPath, qualifier)

      unit.addImport(ResourcePath.resolve(path))
      unit
    } catch {
      case e: Exception =>
        throw new IllegalStateException(s"Namespace '${qualifier}' could not be created from '${path.getPath}'", e)
    }
  }

  def include(scope: Scope, domain: Domain): SourceUnit = {
    val unit: SourceUnit = domain.getSourceUnit(path.getPath)
    val state: ScopeState = scope.getState

    unit.getEntities().forEach(entity => {
      val name: String = entity.getName
      val value: Value = Value.getTransient(entity)

      state.addValue(name, value) // use for static analysis
    })

    unit.getAliases().forEach(alias => {
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

    unit
  }

  def process(scope: Scope, domain: Domain): SourceUnit = {
    domain.getSourceUnit(path.getPath)
  }

  def extend(scope: Scope, domain: Domain): SourceUnit = {
    domain.getSourceUnit(path.getPath)
  }
}
