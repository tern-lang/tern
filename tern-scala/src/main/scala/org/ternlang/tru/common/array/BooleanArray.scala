package org.ternlang.tru.common.array

import org.ternlang.tru.common.array.BooleanArrayCodec.BooleanValueCodec
import org.ternlang.tru.common.message.{ByteBuffer, ByteSize, Flyweight}

trait BooleanArray extends GenericArray[Boolean] {}
trait BooleanArrayBuilder extends BooleanArray with GenericArrayBuilder[Boolean, BooleanValue] {}

trait BooleanValue {
  def get(): Boolean
  def set(value: Boolean): BooleanValue
}

object BooleanArrayCodec {

  final class BooleanValueCodec extends BooleanValue with Flyweight[BooleanValueCodec]{
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): BooleanValueCodec = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def get(): Boolean = {
      buffer.getBoolean(offset)
    }

    override def set(value: Boolean): BooleanValue = {
      buffer.setBoolean(offset, value)
      this
    }
  }
}

final class BooleanArrayCodec
  extends GenericArrayCodec[Boolean, BooleanValue](() => new BooleanValueCodec, value => value.get, ByteSize.BOOL_SIZE)
    with BooleanArrayBuilder
    with Flyweight[BooleanArrayCodec] {}
