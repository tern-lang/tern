package org.ternlang.tru.common.array

import org.ternlang.tru.common.array.IntArrayCodec.IntValueCodec
import org.ternlang.tru.common.message.{ByteBuffer, ByteSize, Flyweight}

trait IntArray extends GenericArray[Int] {}
trait IntArrayBuilder extends IntArray with GenericArrayBuilder[Int, IntValue] {}

trait IntValue {
  def get(): Int
  def set(value: Int): IntValue
}

object IntArrayCodec {

  final class IntValueCodec extends IntValue with Flyweight[IntValueCodec]{
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): IntValueCodec = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def get(): Int = {
      buffer.getInt(offset)
    }

    override def set(value: Int): IntValue = {
      buffer.setInt(offset, value)
      this
    }
  }
}

final class IntArrayCodec
  extends GenericArrayCodec[Int, IntValue](() => new IntValueCodec, value => value.get, ByteSize.INT_SIZE)
    with IntArrayBuilder
    with Flyweight[IntArrayCodec] {}
