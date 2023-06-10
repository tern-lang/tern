package org.ternlang.tru.common.message


import org.agrona.{DirectBuffer, ExpandableArrayBuffer}

import java.io.OutputStream
import java.lang.{Double => DoubleMath, Float => FloatMath}



class ArrayByteBuffer(val size: Int, val cleaner: Option[ByteBuffer => Unit] = None) extends ByteBuffer {
  final private var buffer = new ExpandableArrayBuffer
  private var array = new Array[Byte](size)
  private var offset = 0
  private var count = 0

  def assign(array: Array[Byte], offset: Int, count: Int): ArrayByteBuffer = {
    this.array = array
    this.offset = offset
    this.count = count
    this
  }

  override def getBoolean(index: Int): Boolean = array(offset + index) == 1

  override def setBoolean(index: Int, value: Boolean): ArrayByteBuffer = {
    setCount(index + ByteSize.BOOL_SIZE)
    array(offset + index) = (if (value) 1
    else 0).toByte
    this
  }

  override def getByte(index: Int): Byte = array(offset + index)

  override def setByte(index: Int, value: Byte): ArrayByteBuffer = {
    setCount(index + ByteSize.BYTE_SIZE)
    array(offset + index) = value
    this
  }

  override def getShort(index: Int): Short = {
    var value: Int = 0
    value |= (array(offset + index + 1) & 0xff) << 8
    value |= (array(offset + index) & 0xff)
    value.shortValue
  }

  override def setShort(index: Int, value: Short): ArrayByteBuffer = {
    setCount(index + ByteSize.SHORT_SIZE)
    array(offset + index + 1) = (value >>> 8).toByte
    array(offset + index) = value.toByte
    this
  }

  override def getInt(index: Int): Int = {
    var value: Int = 0
    value |= array(offset + index + 3) << 24
    value |= (array(offset + index + 2) & 0xff) << 16
    value |= (array(offset + index + 1) & 0xff) << 8
    value |= (array(offset + index) & 0xff)
    value
  }

  override def setInt(index: Int, value: Int): ArrayByteBuffer = {
    setCount(index + ByteSize.INT_SIZE)
    array(offset + index + 3) = (value >>> 24).toByte
    array(offset + index + 2) = (value >>> 16).toByte
    array(offset + index + 1) = (value >>> 8).toByte
    array(offset + index) = value.toByte
    this
  }

  override def getLong(index: Int): Long = {
    var value: Long = 0L
    value |= array(offset + index + 7).toLong << 56
    value |= (array(offset + index + 6).toLong & 0xff) << 48
    value |= (array(offset + index + 5).toLong & 0xff) << 40
    value |= (array(offset + index + 4).toLong & 0xff) << 32
    value |= (array(offset + index + 3).toLong & 0xff) << 24
    value |= (array(offset + index + 2).toLong & 0xff) << 16
    value |= (array(offset + index + 1).toLong & 0xff) << 8
    value |= (array(offset + index).toLong & 0xff)
    value
  }

  override def setLong(index: Int, value: Long): ArrayByteBuffer = {
    setCount(index + ByteSize.LONG_SIZE)
    array(offset + index + 7) = (value >>> 56).toByte
    array(offset + index + 6) = (value >>> 48).toByte
    array(offset + index + 5) = (value >>> 40).toByte
    array(offset + index + 4) = (value >>> 32).toByte
    array(offset + index + 3) = (value >>> 24).toByte
    array(offset + index + 2) = (value >>> 16).toByte
    array(offset + index + 1) = (value >>> 8).toByte
    array(offset + index) = value.toByte
    this
  }

  override def getDouble(index: Int): Double = DoubleMath.longBitsToDouble(getLong(index))

  override def setDouble(index: Int, value: Double): ArrayByteBuffer = {
    setLong(index, DoubleMath.doubleToLongBits(value))
    this
  }

  override def getFloat(index: Int): Float = FloatMath.intBitsToFloat(getInt(index))

  override def setFloat(index: Int, value: Float): ArrayByteBuffer = {
    setInt(index, FloatMath.floatToIntBits(value))
    this
  }

  override def getChar(index: Int): Char = {
    var value: Int = 0
    value |= array(offset + index + 1) << 8
    value |= array(offset + index)
    value.toChar
  }

  override def setChar(index: Int, value: Char): ArrayByteBuffer = {
    setCount(index + ByteSize.CHAR_SIZE)
    array(offset + index + 1) = (value >>> 8).toByte
    array(offset + index) = value.toByte
    this
  }

  override def getBytes(index: Int, stream: OutputStream, length: Int): ByteBuffer = {
    if (index + length > this.count) throw new IllegalArgumentException("Buffer length is " + this.count)
    val remainder = length % ByteSize.LONG_SIZE
    val count = length - remainder
    var i = 0
    while ( {
      i < count
    }) {
      val next = getLong(i + index)
      try {
        stream.write(next.toByte & 0xff)
        stream.write((next >>> 8).toByte & 0xff)
        stream.write((next >>> 16).toByte & 0xff)
        stream.write((next >>> 24).toByte & 0xff)
        stream.write((next >>> 32).toByte & 0xff)
        stream.write((next >>> 40).toByte & 0xff)
        stream.write((next >>> 48).toByte & 0xff)
        stream.write((next >>> 56).toByte & 0xff)
      } catch {
        case e: Exception =>
          throw new IllegalStateException("Could not write byte", e)
      }

      i += ByteSize.LONG_SIZE
    }
    for (i <- 0 until remainder) {
      val next = getByte(i + index + count)
      try stream.write(next & 0xff)
      catch {
        case e: Exception =>
          throw new IllegalStateException("Could not write byte", e)
      }
    }
    this
  }

  override def getBytes(index: Int, consumer: (DirectBuffer, Int, Int) => Unit, length: Int): ByteBuffer = {
    if (index + length > this.count) throw new IllegalArgumentException("Buffer length is " + this.count)
    val remainder = length % ByteSize.LONG_SIZE
    val count = length - remainder
    buffer.checkLimit(index + length)
    var i = 0
    while ( {
      i < count
    }) {
      val next = getLong(i + index)
      buffer.putLong(i + offset, next)

      i += ByteSize.LONG_SIZE
    }
    for (i <- 0 until remainder) {
      val next = getByte(i + index + count)
      buffer.putByte(i + offset + count, next)
    }
    consumer.apply(buffer, 0, length)
    this
  }

  override def getBytes(index: Int, buffer: Array[Byte], offset: Int, length: Int): ByteBuffer = {
    if (index + length > this.count) throw new IllegalArgumentException("Buffer length is " + this.count)
    System.arraycopy(array, index, buffer, offset, length)
    this
  }

  override def getBytes(index: Int, buffer: ByteBuffer, offset: Int, length: Int): ByteBuffer = {
    if ((this eq buffer) && offset == index) return this
    if (index + length > this.count) throw new IllegalArgumentException("Buffer length is " + this.count)
    val remainder = length % ByteSize.LONG_SIZE
    val count = length - remainder
    var i = 0
    while ( {
      i < count
    }) {
      val next = getLong(i + index)
      buffer.setLong(i + offset, next)

      i +=ByteSize. LONG_SIZE
    }
    for (i <- 0 until remainder) {
      val next = getByte(i + index + count)
      buffer.setByte(i + offset + count, next)
    }
    this
  }

  override def setCount(index: Int): ByteBuffer = {
    val require = offset + index
    if (array.length < require) {
      val copy = new Array[Byte](require * 2)
      System.arraycopy(array, 0, copy, 0, count)
      array = copy
    }
    if (count <= require) count = require
    this
  }

  override def clear(offset: Int, count: Int): ArrayByteBuffer = {
    var i = offset
    while ( {
      i < count && i < count
    }) {
      array(i) = 0

      i += 1
    }
    this.count = offset
    this
  }

  override def clear: ArrayByteBuffer = {
    for (i <- 0 until count) {
      array(i) = 0
    }
    this.count = 0
    this
  }

  override def dispose: ArrayByteBuffer = {
    cleaner.map(_.apply(this))
    this
  }

  def getByteArray: Array[Byte] = array

  def capacity: Int = array.length

  override def length: Int = count
}
