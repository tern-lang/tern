package trumid.poc.common

import trumid.poc.common.message.{ByteBuffer, ByteSize, DirectByteBuffer, Flyweight}

object ChainTest extends App {

  val buffer = DirectByteBuffer()
  val codec = new PairCodec

  codec.assign(buffer, 0, 1024)
  codec.key(4).value(55)

  if(codec.key() != 4) {
    throw new Error
  }
  if(codec.value()!= 55) {
    throw new Error
  }


  val chain = new Chain[PairBuilder](() => new PairCodec, ByteSize.INT_SIZE * 2)

  buffer.clear()
  chain.assign(buffer, 0, 8192)

  val one = chain.add()
  val two = chain.add()
  val three = chain.add()

  one.key(1).value(11)
  two.key(2).value(22)

  three.key(3).value(33)


  if(two.key() != 2) {
    throw new Error
  }
  if(two.value()!= 22) {
    throw new Error
  }

  if(one.key() != 1) {
    throw new Error
  }
  if(one.value()!= 11) {
    throw new Error
  }
  if(three.key() != 3) {
    throw new Error
  }
  if(three.value()!= 33) {
    throw new Error
  }
  println(chain.size)
  if (chain.size != 3) {
    throw new Error
  }

  if(chain.get(0).get != one) {
    throw new Error
  }
  if(chain.get(1).get.key() != 2) {
    throw new Error
  }
  if(chain.get(1).get.value() != 22) {
    throw new Error
  }

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
  }

}
