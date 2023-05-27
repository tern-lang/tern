package org.ternlang.tru.domain.tree

import org.ternlang.core.module.Path
import org.ternlang.core.scope.{Scope, ScopeState}
import org.ternlang.core.variable.Value
import org.ternlang.tree.Qualifier
import org.ternlang.tru.model._

import java.util.List
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Predicate

class Import(qualifier: Qualifier, path: Path) {
  private val matched = new AtomicReference[Predicate[String]]()

  def define(scope: Scope, domain: Domain): Unit = {
    val resource = SourceNamespace.resolve(qualifier, path)
    val location: String = qualifier.getLocation
    val name: String = qualifier.getName

    if (location == null) {
      throw new IllegalStateException(s"Import has no namespace in ${resource}")
    }
    if (name == null) {
      matched.set((_: String) => true)
    } else {
      matched.set((entry: String) => name == entry)
    }
  }

  def include(scope: Scope, domain: Domain): Unit = {
    val location: String = qualifier.getLocation
    val module: Namespace = domain.getNamespace(location)

    if (module == null) {
      throw new IllegalStateException(s"Could not find namespace ${location} in ${domain.getNamespaces}")
    }
    val entities: List[Entity] = module.getEntities
    val aliases: List[Alias] = module.getAliases
    val constants: List[Constant] = module.getConstants

    include(scope, entities)
    include(scope, aliases)
    include(scope, constants)
  }

  private def include(scope: Scope, imports: List[_ <: Importable]): Unit = {
    val state: ScopeState = scope.getState
    val filter: Predicate[String] = matched.get

    if (filter == null) {
      throw new IllegalStateException("Import not defined")
    }
    imports.forEach(importable => {
      val name: String = importable.getName

      if (filter.test(name)) {
        val value: Value = if (classOf[Value].isInstance(importable)) {
          classOf[Value].cast(importable)
        } else {
          Value.getTransient(importable)
        }
        val existing: Value = state.getValue(name)

        if (existing == null) {
          state.addValue(name, value) // use for static analysis
        } else {
          val resource = SourceNamespace.resolve(qualifier, path)

          if (existing.getValue != value.getValue) {
            throw new IllegalStateException(s"Import '${name}' of value '${existing.getValue}' already exists in ${resource} with value '${value.getValue}'")
          }
        }
      }
    })
  }
}
