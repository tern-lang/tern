package cluster.server.demo.api

import cluster.server.message.{ByteBuffer, ByteSize, Flyweight}

trait CancelOrderCommand {
  def getOrderId: Long
  def getTime: Long
}

object CancelOrderCommandCodec {
  val ORDER_ID_OFFSET: Int = 0
  val ORDER_ID_SIZE: Int = ByteSize.LONG_SIZE
  val TIME_OFFSET: Int = ORDER_ID_OFFSET + ORDER_ID_SIZE
  val TIME_SIZE: Int = ByteSize.DOUBLE_SIZE
  val MESSAGE_SIZE: Int = TIME_OFFSET + TIME_SIZE
}

class CancelOrderCommandCodec extends CancelOrderCommand with Flyweight[CancelOrderCommandCodec] {
  import CancelOrderCommandCodec._

  private var buffer: ByteBuffer = _
  private var offset: Int = _

  override def assign(buffer: ByteBuffer, offset: Int, length: Int): CancelOrderCommandCodec = {
    if (length < MESSAGE_SIZE) {
      throw new IllegalArgumentException(s"Length must be at least $MESSAGE_SIZE")
    }
    this.buffer = buffer
    this.offset = offset
    this
  }

  def withOrderId(orderId: Long): CancelOrderCommandCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + ORDER_ID_OFFSET, orderId)
    this
  }

  def withTime(time: Long): CancelOrderCommandCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + TIME_OFFSET, time)
    this
  }

  override def getOrderId: Long = buffer.getLong(offset + ORDER_ID_OFFSET)

  override def getTime: Long = buffer.getLong(offset + TIME_OFFSET)
}

