package trumid.poc.common.array

import ShortArrayCodec.ShortValueCodec
import trumid.poc.common.message.{ByteBuffer, ByteSize, Flyweight}

trait ShortArray extends GenericArray[Short] {}

trait ShortArrayBuilder extends ShortArray with GenericArrayBuilder[Short, ShortValue] {
  def reset(): ShortArrayBuilder
  def clear(): ShortArrayBuilder
}

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
    with Flyweight[ShortArrayCodec] {

  override def reset(): ShortArrayCodec = {
    chain.reset()
    this
  }

  override def clear(): ShortArrayCodec = {
    chain.clear()
    this
  }
}
