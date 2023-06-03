package org.ternlang.tru.codegen.common

class SourceBuilder {
  private val builder: StringBuilder = new StringBuilder

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

  def append(text: String, values: Any*): SourceBuilder = {
    builder.append(String.format(text, values))
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
