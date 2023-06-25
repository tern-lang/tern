package trumid.poc.common.array

import FloatArrayCodec.FloatValueCodec
import trumid.poc.common.message.{ByteBuffer, ByteSize, Flyweight}

trait FloatArray extends GenericArray[Float] {}

trait FloatArrayBuilder extends FloatArray with GenericArrayBuilder[Float, FloatValue] {
  def reset(): FloatArrayBuilder
  def clear(): FloatArrayBuilder
}

trait FloatValue {
  def get(): Float
  def set(value: Float): FloatValue
}

object FloatArrayCodec {

  final class FloatValueCodec extends FloatValue with Flyweight[FloatValueCodec]{
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): FloatValueCodec = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def get(): Float = {
      buffer.getFloat(offset)
    }

    override def set(value: Float): FloatValue = {
      buffer.setFloat(offset, value)
      this
    }
  }
}

final class FloatArrayCodec
  extends GenericArrayCodec[Float, FloatValue](() => new FloatValueCodec, value => value.get, ByteSize.FLOAT_SIZE)
    with FloatArrayBuilder
    with Flyweight[FloatArrayCodec] {

  override def reset(): FloatArrayCodec = {
    chain.reset()
    this
  }

  override def clear(): FloatArrayCodec = {
    chain.clear()
    this
  }
}
