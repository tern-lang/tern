package org.ternlang.tru.common.array

import org.ternlang.tru.common.array.ByteArrayCodec.ByteValueCodec
import org.ternlang.tru.common.message.{ByteBuffer, ByteSize, Flyweight}

trait ByteArray extends GenericArray[Byte] {}
trait ByteArrayBuilder extends ByteArray with GenericArrayBuilder[Byte, ByteValue] {}

trait ByteValue {
  def get(): Byte
  def set(value: Byte): ByteValue
}

object ByteArrayCodec {

  final class ByteValueCodec extends ByteValue with Flyweight[ByteValueCodec]{
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): ByteValueCodec = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def get(): Byte = {
      buffer.getByte(offset)
    }

    override def set(value: Byte): ByteValue = {
      buffer.setByte(offset, value)
      this
    }
  }
}

final class ByteArrayCodec
  extends GenericArrayCodec[Byte, ByteValue](() => new ByteValueCodec, value => value.get, ByteSize.BYTE_SIZE)
    with ByteArrayBuilder
    with Flyweight[ByteArrayCodec] {}
