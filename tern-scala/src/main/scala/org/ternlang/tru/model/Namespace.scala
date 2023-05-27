package org.ternlang.tru.model

import org.ternlang.core.scope.Scope
import org.ternlang.core.variable.Value
import org.ternlang.tru.model.Namespace._
import org.ternlang.tru.text.PascalCaseStyle

import java.util
import java.util.Collections
import java.util.concurrent.{ConcurrentHashMap, CopyOnWriteArrayList, CopyOnWriteArraySet}

class Namespace(domain: Domain, name: String) {
  private val resolver = new NamespaceResolver(this)
  private val constants = new ConstantSet(this)
  private val entities = new EntitySet(this)
  private val aliases = new AliasSet(this)
  private val imports = new ImportSet(this)
  private var scope: Scope = null;

  def getName(): String = name
  def getScope(): Scope = scope
  def setScope(scope: Scope): Namespace = {
    this.scope = scope
    this
  }

  def addImport(path: String): Namespace = imports.addImport(path)
  def getImports(): util.Set[String] = imports.getImports()
  def addConstant(name: String, value: Any): Constant = constants.addElement(name).setConstant(value)
  def getConstant(name: String): Constant = constants.getElement(name)
  def getConstants(): util.List[Constant] = constants.getElements()
  def addEntity(name: String): Entity = entities.addElement(name)
  def getEntity(name: String): Entity = entities.getElement(name)
  def getEntities(): util.List[Entity] = entities.getElements()
  def getVisibleEntity(name: String): Entity = resolver.getVisibleEntity(name)
  def addAlias(name: String): Alias = aliases.addElement(name)
  def getAlias(name: String): Alias = aliases.getElement(name)
  def getAliases(): util.List[Alias] = aliases.getElements()
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

  abstract private class NamespaceSet[T](module: Namespace) {
    private val elements: util.Map[String, T] = new ConcurrentHashMap[String, T]
    private val declared: util.List[T] = new CopyOnWriteArrayList[T]

    def addElement(name: String): T = {
      var element: T = elements.get(name)

      if (element != null) {
        throw new IllegalStateException(PascalCaseStyle.toCase(getType) + " '" + name + "' already created for '" + module.getName + "'")
      }
      element = newElement(name, module)
      elements.put(name, element)
      declared.add(element)
      element
    }

    def getElement(name: String): T = elements.get(name)
    def getElements(): util.List[T] = Collections.unmodifiableList(declared)
    def isPresent(name: String): Boolean = elements.containsKey(name)
    protected def getType: String
    protected def newElement(name: String, module: Namespace): T
  }

  private class ConstantSet(module: Namespace) extends Namespace.NamespaceSet[Constant](module) {
    override protected def newElement(name: String, module: Namespace) = new Constant(name)
    override protected def getType = "constant"
  }

  private class EntitySet(module: Namespace) extends Namespace.NamespaceSet[Entity](module) {
    override protected def newElement(name: String, module: Namespace) = new Entity(module, name)
    override protected def getType = "entity"
  }

  private class AliasSet(module: Namespace) extends Namespace.NamespaceSet[Alias](module) {
    override protected def newElement(name: String, module: Namespace) = new Alias(name)
    override protected def getType = "alias"
  }

  private class ImportSet(module: Namespace) {
    private val paths = new CopyOnWriteArraySet[String]()
    def addImport(path: String): Namespace = {
      paths.add(path)
      module
    }
    def getImports(): util.Set[String] = {
      paths
    }
  }
}
