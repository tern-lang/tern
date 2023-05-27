package org.ternlang.tru.domain.tree

import org.ternlang.core.module.Path
import org.ternlang.core.scope.{Scope, ScopeState}
import org.ternlang.core.variable.Value
import org.ternlang.tru.model.{Domain, Namespace}
import org.ternlang.tree.Qualifier

class SourceNamespace(qualifier: Qualifier, path: Path) {
  private val resource: String = SourceNamespace.resolve(qualifier, path)

  def define(scope: Scope, domain: Domain): Namespace = {
    val location: String = qualifier.getQualifier

    try {
      domain.addNamespace(location).setPath(resource)
    } catch {
      case e: Exception =>
        throw new IllegalStateException(s"Namespace '${location}' could not be created from '${resource}'", e)
    }
  }

  def include(scope: Scope, domain: Domain): Namespace = {
    val location: String = qualifier.getQualifier
    val namespace: Namespace = domain.getNamespace(location)
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
    domain.getNamespace(qualifier.getQualifier)
  }

  def extend(scope: Scope, domain: Domain): Namespace = {
    domain.getNamespace(qualifier.getQualifier)
  }
}

object SourceNamespace {

  def resolve(qualifier: Qualifier, path: Path): String = {
    var resource: String = path.getPath

    if (resource.startsWith("/")) {
      resource = resource.substring(1)
    }
    if (resource.endsWith("/idl.tern")) {
      resource = resource.replace("/idl.tern", ".idl")
    }
    resource
  }
}
