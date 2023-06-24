package trumid.poc.impl.server.message

import ByteSize.LONG_SIZE
import org.agrona.DirectBuffer

import java.io.OutputStream

final class DirectBufferWrapper extends ByteBuffer {
  private var buffer: DirectBuffer = _
  private var offset: Int = _
  private var len: Int = _

  def wrap(buffer: DirectBuffer, offset: Int, length: Int): this.type = {
    this.buffer = buffer
    this.offset = offset
    this.len = length
    this
  }

  override def getBoolean(index: Int): Boolean =
    buffer.getByte(offset + index) != 0

  override def getByte(index: Int): Byte =
    buffer.getByte(offset + index)

  override def getShort(index: Int): Short =
    buffer.getShort(offset + index)

  override def getInt(index: Int): Int =
    buffer.getInt(offset + index)

  override def getLong(index: Int): Long =
    buffer.getLong(offset + index)

  override def getDouble(index: Int): Double =
    buffer.getDouble(offset + index)

  override def getFloat(index: Int): Float =
    buffer.getFloat(offset + index)

  override def getChar(index: Int): Char =
    buffer.getChar(offset + index)

  override def getBytes(index: Int, stream: OutputStream, length: Int): ByteBuffer = {
    if (index + length > this.len) {
      throw new IllegalArgumentException(s"Buffer length is $length")
    }
    val remainder = length % LONG_SIZE
    val count = length - remainder

    for (i <- 0 until count by LONG_SIZE) {
      val next = getLong(i + index)
      try {
        stream.write((next & 0xff).toByte)
        stream.write((next >>> 8 & 0xff).toByte)
        stream.write((next >>> 16 & 0xff).toByte)
        stream.write((next >>> 24 & 0xff).toByte)
        stream.write((next >>> 32 & 0xff).toByte)
        stream.write((next >>> 40 & 0xff).toByte)
        stream.write((next >>> 48 & 0xff).toByte)
        stream.write((next >>> 56 & 0xff).toByte)
      } catch {
        case e: Exception => throw new IllegalStateException("Could not write byte", e)
      }
    }

    for (i <- 0 until remainder) {
      val next = getByte(i + index + count)
      try {
        stream.write(next & 0xff)
      } catch {
        case e: Exception => throw new IllegalStateException("Could not write byte", e)
      }
    }
    this
  }

  override def getBytes(index: Int, array: Array[Byte], offset: Int, length: Int): DirectBufferWrapper = {
    if (index + length > this.len) {
      throw new IllegalArgumentException("Buffer length is " + this.len)
    }
    val remainder = length % LONG_SIZE
    val count = length - remainder
    for (i <- 0 until count by LONG_SIZE) {
      val next = getLong(i + index)
      array(i + offset) = (next & 0xFF).toByte
      array(i + offset + 1) = (next >>> 8 & 0xFF).toByte
      array(i + offset + 2) = (next >>> 16 & 0xFF).toByte
      array(i + offset + 3) = (next >>> 24 & 0xFF).toByte
      array(i + offset + 4) = (next >>> 32 & 0xFF).toByte
      array(i + offset + 5) = (next >>> 40 & 0xFF).toByte
      array(i + offset + 6) = (next >>> 48 & 0xFF).toByte
      array(i + offset + 7) = (next >>> 56 & 0xFF).toByte
    }
    for (i <- 0 until remainder) {
      val next = getByte(i + index + count)
      array(i + offset + count) = next
    }
    this
  }

  override def getBytes(index: Int, buffer: ByteBuffer, offset: Int, length: Int): DirectBufferWrapper = {
    if (this == buffer && offset == index) {
      return this
    }
    if (index + length > this.len) {
      throw new IllegalArgumentException("Buffer length is " + this.len)
    }
    val remainder = length % LONG_SIZE
    val count = length - remainder
    for (i <- 0 until count by LONG_SIZE) {
      val next = getLong(i + index)
      buffer.setLong(i + offset, next)
    }
    for (i <- 0 until remainder) {
      val next = getByte(i + index + count)
      buffer.setLong(i + offset + count, next)
    }
    this
  }

  override def getBytes(index: Int, consumer: (DirectBuffer, Int, Int) => Unit, length: Int): DirectBufferWrapper = {
    if (index + length > this.len) {
      throw new IllegalArgumentException("Buffer length is " + this.len)
    }
    consumer.apply(buffer, index + offset, length)
    this
  }

  override def setBoolean(index: Int, value: Boolean): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def setByte(index: Int, value: Byte): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def setShort(index: Int, value: Short): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def setInt(index: Int, value: Int): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def setLong(index: Int, value: Long): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def setDouble(index: Int, value: Double): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def setFloat(index: Int, value: Float): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def setChar(index: Int, value: Char): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def setCount(count: Int): DirectBufferWrapper = {
    if (count > len) {
      throw new IllegalStateException(s"Length $count is larger than capacity $len")
    }
    this
  }

  override def clear(index: Int, count: Int): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def clear(): DirectBufferWrapper = {
    throw new UnsupportedOperationException("Illegal modification of buffer")
  }

  override def dispose(): DirectBufferWrapper = this

  override def length(): Int = len
}



