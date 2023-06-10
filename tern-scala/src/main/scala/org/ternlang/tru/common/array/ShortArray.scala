package org.ternlang.tru.common.array

import org.ternlang.tru.common.array.ShortArrayCodec.ShortValueCodec
import org.ternlang.tru.common.message.{ByteBuffer, ByteSize, Flyweight}

trait ShortArray extends GenericArray[Short] {}
trait ShortArrayBuilder extends ShortArray with GenericArrayBuilder[Short, ShortValue] {}

trait ShortValue {
  def get(): Short
  def set(value: Short): ShortValue
}

object ShortArrayCodec {

  final class ShortValueCodec extends ShortValue with Flyweight[ShortValueCodec]{
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): ShortValueCodec = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def get(): Short = {
      buffer.getShort(offset)
    }

    override def set(value: Short): ShortValue = {
      buffer.setShort(offset, value)
      this
    }
  }
}

final class ShortArrayCodec
  extends GenericArrayCodec[Short, ShortValue](() => new ShortValueCodec, value => value.get, ByteSize.SHORT_SIZE)
    with ShortArrayBuilder
    with Flyweight[ShortArrayCodec] {}
