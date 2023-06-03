package org.ternlang.tru.codegen.common

class SourceBuilder(builder: StringBuilder = StringBuilder.newBuilder){

  def append(number: Int): SourceBuilder = {
    builder.append(number)
    this
  }

  def append(number: Long): SourceBuilder = {
    builder.append(number)
    this
  }

  def append(text: String): SourceBuilder = {
    builder.append(text)
    this
  }

  def reset: SourceBuilder = {
    builder.setLength(0)
    this
  }

  override def toString: String = {
    builder.toString
  }
}
