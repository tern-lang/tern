package trumid.poc.dsl.tree

import org.ternlang.core.module.Path
import org.ternlang.core.scope.{Scope, ScopeState}
import org.ternlang.core.variable.Value
import trumid.poc.model.{Domain, SourceUnit}

class SourceFile(val qualifier: String, val path: Path) {

  def define(scope: Scope, domain: Domain): SourceUnit = {
    try {
      domain.addSourceUnit(path, qualifier)
    } catch {
      case e: Exception =>
        throw new IllegalStateException(s"Namespace '${qualifier}' could not be created from '${path.getPath}'", e)
    }
  }

  def include(scope: Scope, domain: Domain): SourceUnit = {
    val unit: SourceUnit = domain.getSourceUnit(path.getPath)
    val inner: Scope = unit.getNamespace().getScope()
    val state: ScopeState = inner.getState()

    unit.getEntities().forEach(entity => {
      val name: String = entity.getName()
      val value: Value = Value.getTransient(entity)

      state.addValue(name, value) // use for static analysis
    })

    unit.getAliases().forEach(alias => {
      val name: String = alias.getName()
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
