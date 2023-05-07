package cluster.server.io

import org.agrona.DirectBuffer

import java.io.OutputStream

trait ByteBuffer {
  def getBoolean(index: Int): Boolean
  def getByte(index: Int): Byte
  def getShort(index: Int): Short
  def getInt(index: Int): Int
  def getLong(index: Int): Long
  def getDouble(index: Int): Double
  def getFloat(index: Int): Float
  def getChar(index: Int): Char
  def getBytes(index: Int, buffer: Array[Byte], offset: Int, length: Int): ByteBuffer
  def getBytes(index: Int, stream: OutputStream, length: Int): ByteBuffer
  def getBytes(index: Int, buffer: ByteBuffer, offset: Int, length: Int): ByteBuffer
  def getBytes(index: Int, consumer: (DirectBuffer, Int, Int) => Unit, length: Int): ByteBuffer
  def setBoolean(index: Int, value: Boolean): ByteBuffer
  def setByte(index: Int, value: Byte): ByteBuffer
  def setShort(index: Int, value: Short): ByteBuffer
  def setInt(index: Int, value: Int): ByteBuffer
  def setLong(index: Int, value: Long): ByteBuffer
  def setDouble(index: Int, value: Double): ByteBuffer
  def setFloat(index: Int, value: Float): ByteBuffer
  def setChar(index: Int, value: Char): ByteBuffer
  def setCount(count: Int): ByteBuffer
  def clear(index: Int, length: Int): ByteBuffer
  def clear(): ByteBuffer
  def dispose(): ByteBuffer
  def length(): Int
}
