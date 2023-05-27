package org.ternlang.tru.model

import org.ternlang.core.variable.Value

class Constant(name: String) extends Value with Importable{
  private var value: Any = null

  override def setValue(value: Any): Unit = {
    setConstant(value)
  }

  def setConstant(value: Any): Constant = {
    this.value = value
    this
  }

  override def getName: String = name
  override def getValue[T](): T = value.asInstanceOf[T]
  override def toString: String = String.valueOf(value)
}
