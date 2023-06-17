package trumid.poc.dsl.tree.imports

import org.ternlang.core.module.Path
import org.ternlang.core.scope.{Scope, ScopeState}
import org.ternlang.core.variable.Value
import trumid.poc.model._

import java.util.List
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Predicate

class Import(qualifier: Qualifier, path: Path) {
  private val matched = new AtomicReference[Predicate[String]]()

  def define(scope: Scope, unit: SourceUnit): Unit = {
    val location: String = qualifier.getLocation()
    val predicate: Predicate[String] = qualifier.getPredicate()

    if (location == null) {
      throw new IllegalStateException(s"Import has no path in ${location}")
    }
    unit.addImport(location)
    matched.set(predicate)
  }

  def include(scope: Scope, unit: SourceUnit): Unit = {
    val location = qualifier.getLocation()
    val other = unit.getDomain().getSourceUnit(location)
    val entities: List[Entity] = other.getEntities()
    val aliases: List[Alias] = other.getAliases()
    val constants: List[Constant] = other.getConstants()

    include(scope, entities)
    include(scope, aliases)
    include(scope, constants)
  }

  private def include(scope: Scope, imports: List[_ <: Importable]): Unit = {
    val state: ScopeState = scope.getState()
    val filter: Predicate[String] = matched.get()

    if (filter == null) {
      throw new IllegalStateException("Import not defined")
    }
    imports.forEach(importable => {
      val name: String = importable.getName()

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
          if (existing.getValue != value.getValue) {
            throw new IllegalStateException(s"Import '${name}' of value '${existing.getValue}' already exists " +
              s"in ${qualifier.getLocation} with value '${value.getValue}'")
          }
        }
      }
    })
  }
}
