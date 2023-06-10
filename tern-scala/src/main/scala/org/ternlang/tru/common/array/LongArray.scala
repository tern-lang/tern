package org.ternlang.tru.common.array

import org.ternlang.tru.common.array.LongArrayCodec.LongValueCodec
import org.ternlang.tru.common.message.{ByteBuffer, ByteSize, Flyweight}

trait LongArray extends GenericArray[Long] {}
trait LongArrayBuilder extends LongArray with GenericArrayBuilder[Long, LongValue] {}

trait LongValue {
  def get(): Long
  def set(value: Long): LongValue
}

object LongArrayCodec {

  final class LongValueCodec extends LongValue with Flyweight[LongValueCodec]{
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): LongValueCodec = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def get(): Long = {
      buffer.getLong(offset)
    }

    override def set(value: Long): LongValue = {
      buffer.setLong(offset, value)
      this
    }
  }
}

final class LongArrayCodec
  extends GenericArrayCodec[Long, LongValue](() => new LongValueCodec, value => value.get, ByteSize.LONG_SIZE)
    with LongArrayBuilder
    with Flyweight[LongArrayCodec] {}
