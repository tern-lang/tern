package org.ternlang.tru.model

import org.ternlang.core.annotation.Annotation
import org.ternlang.tru.model.Entity.PropertySet

import java.util
import java.util.concurrent.{ConcurrentHashMap, CopyOnWriteArrayList}
import java.util.{Collections, List, Map}

class Entity(namespace: Namespace, name: String) extends Importable with Annotated {
  private val annotations: Map[String, Annotation] = new ConcurrentHashMap[String, Annotation]()
  private val properties: PropertySet = new PropertySet(this)
  private var category: Category = null
  private var extend: String = null

  def getName(): String = name;

  def getCategory(): Category = category

  def setCategory(category: Category): Entity = {
    this.category = category
    this
  }

  def getExtends(): String = extend

  def setExtends(extend: String): Entity = {
    this.extend = extend
    this
  }

  def getNamespace(): Namespace = namespace

  def getProperties(): List[Property] = properties.getProperties
  def getProperty(name: String): Property = properties.getProperty(name)
  def addProperty(name: String): Property = properties.addProperty(name)

  override def getAnnotations(): Map[String, Annotation] = annotations


}

object Entity {
  private class PropertySet(val entity: Entity) {
    private val comparator =  new PropertyComparator
    private val properties = new ConcurrentHashMap[String, Property]
    private val declared = new CopyOnWriteArrayList[Property]
    private var ordered = Collections.emptyList[Property]()

    def isPropertyPresent(name: String): Boolean = properties.containsKey(name)

    def addProperty(name: String): Property = {
      var property = properties.get(name)
      val parent = entity.getName

      if (property != null) {
        throw new IllegalStateException("Property '" + name + "' already exists for '" + parent + "'")
      }
      property = new Property(name)
      properties.put(name, property)
      declared.add(property)
      updateProperties()
      property
    }

    def getProperty(name: String): Property = properties.get(name)
    def getProperties: util.List[Property] = getProperties(SortedOrder)
    def getProperties(order: PropertyOrder): util.List[Property] =
      if (order.isDeclaration) {
        Collections.unmodifiableList(declared)
      } else {
        ordered
      }

    private def updateProperties(): Unit = {
      val list = new util.ArrayList[Property](declared)
      var index = 0

      Collections.sort(list, comparator) // important to align consistently
      list.forEach(property => {
        property.setIndex({
          index += 1; index - 1
        })
      })
      this.ordered = Collections.unmodifiableList(list)
    }
  }
}
