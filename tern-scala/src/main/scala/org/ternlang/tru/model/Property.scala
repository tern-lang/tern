package org.ternlang.tru.model

import org.ternlang.core.annotation.Annotation

import java.util.Map
import java.util.concurrent.ConcurrentHashMap

class Property(name: String) extends Variable with Annotated {
  private val annotations = new ConcurrentHashMap[String, Annotation]()
  private var constraint: String = null
  private var default: Any = null
  private var index: Int = 0

  def copy(template: Property): Property = {
    this
  }

  def getIndex(): Int = index

  def setIndex(index: Int): Property = {
    this.index = index
    this
  }

  def getConstraint(): String = constraint

  def setConstraint(constraint: String): Property = {
    this.constraint = constraint
    this
  }

  def getDefaultValue(): Any = default

  def setDefaultValue(default: Any): Property = {
    this.default = default
    this
  }

  override def getName: String = name

  override def getAnnotations(): Map[String, Annotation] = annotations

  def isDynamic(): Boolean = false
}

sealed trait PropertyOrder {
  def isDeclaration(): Boolean = false

  def isSorted(): Boolean = false
}

object DeclarationOrder extends PropertyOrder {
  override def isDeclaration(): Boolean = true
}

object SortedOrder extends PropertyOrder {
  override def isSorted(): Boolean = true
}
