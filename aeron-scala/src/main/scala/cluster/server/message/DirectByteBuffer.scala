package cluster.server.message

import cluster.server.message.ByteSize.LONG_SIZE
import org.agrona.{DirectBuffer, ExpandableDirectByteBuffer, MutableDirectBuffer}

import java.io.OutputStream
import java.nio.ByteOrder

object DirectByteBuffer {
  def apply(order: ByteOrder) = new DirectByteBuffer(order, 8192, null)
  def apply(order: ByteOrder, initialCapacity: Int) =  new DirectByteBuffer(order, initialCapacity, null)
  def apply(order: ByteOrder, initialCapacity: Int, cleaner: (ByteBuffer) => Unit) =  new DirectByteBuffer(order, initialCapacity, cleaner)
}

final class DirectByteBuffer(private val order: ByteOrder, capacity: Int, cleaner: (ByteBuffer) => Unit) extends ByteBuffer {
  private val buffer: MutableDirectBuffer = new ExpandableDirectByteBuffer(capacity)
  private var len: Int = 0

  def this(order: ByteOrder) = this(order, 8192, null)

  def this(order: ByteOrder, initialCapacity: Int) = this(order, initialCapacity, null)

  def getBoolean(index: Int): Boolean = buffer.getByte(index) != 0

  def getByte(index: Int): Byte = buffer.getByte(index)

  def getShort(index: Int): Short = buffer.getShort(index, order)

  def getInt(index: Int): Int = buffer.getInt(index, order)

  def getLong(index: Int): Long = buffer.getLong(index, order)

  def getDouble(index: Int): Double = buffer.getDouble(index, order)

  def getFloat(index: Int): Float = buffer.getFloat(index, order)

  def getChar(index: Int): Char = buffer.getChar(index, order)

  def getBytes(index: Int, stream: OutputStream, length: Int): DirectByteBuffer = {
    if (index + length > this.len) throw new IllegalArgumentException("Buffer length is " + this.len)

    val remainder = length % LONG_SIZE
    val count = length - remainder

    var i = 0
    while (i < count) {
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
      i += LONG_SIZE
    }
    i = 0
    while (i < remainder) {
      val next = getByte(i + index + count)

      try {
        stream.write((next & 0xff).toByte)
      } catch {
        case e: Exception => throw new IllegalStateException("Could not write byte", e)
      }
      i += 1
    }
    this
  }

  override def getBytes(index: Int, array: Array[Byte], offset: Int, length: Int): DirectByteBuffer = {
    if (index + length > length) {
      throw new IllegalArgumentException(s"Buffer length is $length")
    }
    val remainder = length % LONG_SIZE
    val count = length - remainder
    for (i <- 0 until count by LONG_SIZE) {
      val next = getLong(i + index)
      array(i + offset) = next.toByte
      array(i + offset + 1) = (next >>> 8).toByte
      array(i + offset + 2) = (next >>> 16).toByte
      array(i + offset + 3) = (next >>> 24).toByte
      array(i + offset + 4) = (next >>> 32).toByte
      array(i + offset + 5) = (next >>> 40).toByte
      array(i + offset + 6) = (next >>> 48).toByte
      array(i + offset + 7) = (next >>> 56).toByte
    }
    for (i <- 0 until remainder) {
      val next = getByte(i + index + count)
      array(i + offset + count) = next
    }
    this
  }


  def getBytes(index: Int, buffer: ByteBuffer, offset: Int, length: Int): DirectByteBuffer = {
    if (this == buffer && offset == index) {
      return this
    }
    if (index + length > this.len) {
      throw new IllegalArgumentException("Buffer length is " + this.len)
    }
    buffer match {
      case db: DirectByteBuffer => getBytes(index, db, offset, length)
      case _ =>
        val remainder = length % LONG_SIZE
        val count = length - remainder
        for (i <- 0 until count by LONG_SIZE) {
          val next = getLong(i + index)
          buffer.setLong(i + offset, next)
        }
        for (i <- 0 until remainder) {
          val next = getByte(i + index + count)
          buffer.setByte(i + offset + count, next)
        }
    }
    this
  }

  def getBytes(index: Int, buffer: DirectByteBuffer, offset: Int, length: Int): DirectByteBuffer = {
    buffer.setCount(offset + length)
    this.buffer.getBytes(index, buffer.buffer, offset, length)
    this
  }

  override def getBytes(index: Int, consumer: (DirectBuffer, Int, Int) => Unit, length: Int): DirectByteBuffer = {
    if (index + length > this.len) throw new IllegalArgumentException("Buffer length is " + this.len)
    consumer.apply(buffer, index, length)
    this
  }

  override def setBoolean(index: Int, value: Boolean): DirectByteBuffer = {
    setCount(index + ByteSize.BOOL_SIZE)
    buffer.putByte(index, (if (value) 1 else 0).toByte)
    this
  }

  override def setByte(index: Int, value: Byte): DirectByteBuffer = {
    setCount(index + ByteSize.BYTE_SIZE)
    buffer.putByte(index, value)
    this
  }

  override def setShort(index: Int, value: Short): DirectByteBuffer = {
    setCount(index + ByteSize.SHORT_SIZE)
    buffer.putShort(index, value, order)
    this
  }

  override def setInt(index: Int, value: Int): DirectByteBuffer = {
    setCount(index + ByteSize.INT_SIZE)
    buffer.putInt(index, value, order)
    this
  }

  override def setLong(index: Int, value: Long): DirectByteBuffer = {
    setCount(index + ByteSize.LONG_SIZE)
    buffer.putLong(index, value, order)
    this
  }

  override def setDouble(index: Int, value: Double): DirectByteBuffer = {
    setCount(index + ByteSize.DOUBLE_SIZE)
    buffer.putDouble(index, value, order)
    this
  }

  override def setFloat(index: Int, value: Float): DirectByteBuffer = {
    setCount(index + ByteSize.FLOAT_SIZE)
    buffer.putFloat(index, value, order)
    this
  }

  override def setChar(index: Int, value: Char): DirectByteBuffer = {
    setCount(index + ByteSize.CHAR_SIZE)
    buffer.putChar(index, value, order)
    this
  }

  override def setCount(count: Int): DirectByteBuffer = {
    if (count >= capacity) buffer.checkLimit(count)
    if (len <= count) len = count
    this
  }

  override def clear(offset: Int, count: Int): DirectByteBuffer = {
    buffer.setMemory(offset, Math.min(count, len - offset), 0.asInstanceOf[Byte])
    len = offset
    this
  }

  override def clear(): DirectByteBuffer = {
    buffer.setMemory(0, len, 0.asInstanceOf[Byte])
    len = 0
    this
  }

  override def dispose(): DirectByteBuffer = {
    if (cleaner != null) {
      cleaner.apply(this)
    }
    this
  }

  override def length(): Int = len
}