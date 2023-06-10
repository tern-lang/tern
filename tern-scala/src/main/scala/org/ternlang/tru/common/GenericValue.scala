package org.ternlang.tru.common

import org.ternlang.tru.common.message.{ByteBuffer, Flyweight}


trait GenericValue[T] {
  def get(): T
  def set(value: T): GenericValue[T]
}

class GenericValueCodec[T](get: (ByteBuffer, Int) => T, set: (ByteBuffer, Int, T) => Unit) extends GenericValue[T] with Flyweight[GenericValue[T]] {
  protected var buffer: ByteBuffer = null
  protected var offset: Int = 0
  protected var length: Int = 0

  override def assign(buffer: ByteBuffer, offset: Int, length: Int): GenericValue[T] = {
    this.buffer = buffer;
    this.offset = offset;
    this.length = length;
    this;
  }

  override def get(): T = {
    get.apply(buffer, offset)
  }

  override def set(value: T): GenericValue[T] = {
    set.apply(buffer, offset, value)
    this
  }
}