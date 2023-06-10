package org.ternlang.tru.common.array

import org.ternlang.tru.common.array.DoubleArrayCodec.DoubleValueCodec
import org.ternlang.tru.common.message.{ByteBuffer, ByteSize, Flyweight}

trait DoubleArray extends GenericArray[Double] {}
trait DoubleArrayBuilder extends DoubleArray with GenericArrayBuilder[Double, DoubleValue] {}

trait DoubleValue {
  def get(): Double
  def set(value: Double): DoubleValue
}

object DoubleArrayCodec {

  final class DoubleValueCodec extends DoubleValue with Flyweight[DoubleValueCodec]{
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): DoubleValueCodec = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def get(): Double = {
      buffer.getDouble(offset)
    }

    override def set(value: Double): DoubleValue = {
      buffer.setDouble(offset, value)
      this
    }
  }
}

final class DoubleArrayCodec
    extends GenericArrayCodec[Double, DoubleValue](() => new DoubleValueCodec, value => value.get, ByteSize.DOUBLE_SIZE)
      with DoubleArrayBuilder
      with Flyweight[DoubleArrayCodec] {}
