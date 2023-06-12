package trumid.poc.model

import org.ternlang.core.scope.Scope
import org.ternlang.core.variable.Value
import Namespace._

import java.util
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

class Namespace(domain: Domain, name: String) {
  private val resolver = new NamespaceResolver(this)
  private val entities = new ConcurrentHashMap[String, Entity]
  private val constants = new ConcurrentHashMap[String, Constant]
  private val aliases = new ConcurrentHashMap[String, Alias]
  private var scope: Scope = null;

  def getName(): String = name

  def getVersion(): Version = domain.getVersion()

  def getScope(): Scope = scope

  def setScope(inner: Scope): Namespace = {
    if (inner == null) {
      throw new IllegalArgumentException(s"Scope is null for namespace ${name}")
    }
    if (scope != null) {
      throw new IllegalArgumentException(s"Scope already set for namespace ${name}")
    }
    scope = inner
    this
  }

  def getVisibleEntity(name: String): Entity = resolver.getVisibleEntity(name)

  def getEntity(name: String): Entity = entities.get(name)

  def getEntities(): util.List[Entity] = entities.values().stream().collect(Collectors.toList())

  def addEntity(entity: Entity): Namespace = {
    entities.put(entity.getName, entity)
    this
  }

  def getConstant(name: String): Constant = constants.get(name)

  def addConstant(constant: Constant): Namespace = {
    constants.put(constant.getName, constant)
    this
  }

  def getAlias(name: String): Alias = aliases.get(name)

  def addAliases(alias: Alias): Namespace = {
    aliases.put(alias.getName, alias)
    this
  }

  override def toString() = name
}

object Namespace {

  private class NamespaceResolver(parent: Namespace) {
    private val cache = new ConcurrentHashMap[String, Value]

    def getVisibleEntity(name: String): Entity = {
      val entity = parent.getEntity(name)

      if (entity == null) {
        cache.computeIfAbsent(name, _ => getValue(name)).getValue.asInstanceOf[Entity]
      } else {
        entity
      }
    }

    private def getValue(name: String): Value = {
      val scope = parent.getScope

      if (scope != null) {
        val state = scope.getState
        val result = state.getValue(name)

        if (result != null) {
          return result
        }
      }
      Value.NULL
    }
  }
}
