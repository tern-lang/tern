package trumid.poc.common.array

import IntArrayCodec.IntValueCodec
import trumid.poc.common.message.{ByteBuffer, ByteSize, Flyweight}

trait IntArray extends GenericArray[Int] {}

trait IntArrayBuilder extends IntArray with GenericArrayBuilder[Int, IntValue] {
  def reset(): IntArrayBuilder
  def clear(): IntArrayBuilder
}

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
    with Flyweight[IntArrayCodec] {

  override def reset(): IntArrayCodec = {
    chain.reset()
    this
  }

  override def clear(): IntArrayCodec = {
    chain.clear()
    this
  }
}
