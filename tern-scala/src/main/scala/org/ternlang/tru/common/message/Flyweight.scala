package org.ternlang.tru.common.message

trait Flyweight[T] {
  def assign(buffer: ByteBuffer, offset: Int, length: Int): T
  def reset(): Flyweight[T] = {
    this
  }
}
