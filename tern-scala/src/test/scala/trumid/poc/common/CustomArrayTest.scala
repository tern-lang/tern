package trumid.poc.common

import trumid.poc.common.array.{GenericArray, GenericArrayBuilder, GenericArrayCodec}
import trumid.poc.common.message.{ByteBuffer, ByteSize, DirectByteBuffer, Flyweight}

object CustomArrayTest extends App{
  val buffer = DirectByteBuffer()
  val codec = new PairArrayCodec()

  codec.assign(buffer, 0, 1024)
  codec.add().key(22).value(33)
  codec.add().key(44).value(55)
  codec.add().key(66).value(77)

  if(codec.size != 3) {
    throw new Error
  }
  if(codec.get(1).get.key() != 44) {
    throw new Error
  }
  codec.iterator().foreach(d => println(d))

  trait PairArray extends GenericArray[Pair] {}
  trait PairArrayBuilder extends PairArray with GenericArrayBuilder[Pair, PairBuilder] {}

  final class PairArrayCodec
    extends GenericArrayCodec[Pair, PairBuilder](() => new PairCodec, value => value, ByteSize.INT_SIZE * 2)
      with PairArrayBuilder
      with Flyweight[PairArrayCodec] {}

  trait Pair {
    def key(): Int
    def value(): Int
  }

  trait PairBuilder extends Pair {
    def key(key: Int): PairBuilder
    def value(value: Int): PairBuilder
  }

  class PairCodec extends PairBuilder with Flyweight[PairBuilder] {
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): PairBuilder = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def key(): Int = {
      buffer.getInt(offset)
    }

    override def key(key: Int): PairCodec = {
      buffer.setInt(offset, key)
      this
    }

    override def value(): Int = {
      buffer.getInt(offset + ByteSize.INT_SIZE)
    }

    override def value(value: Int): PairCodec = {
      buffer.setInt(offset + ByteSize.INT_SIZE, value)
      this
    }

    override def toString(): String =  {
      s"key=${key()} value=${value()}"
    }
  }

}
