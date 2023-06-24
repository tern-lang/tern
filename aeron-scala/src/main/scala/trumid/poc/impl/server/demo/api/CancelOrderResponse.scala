package trumid.poc.impl.server.demo.api

import trumid.poc.impl.server.message.{ByteBuffer, ByteSize, Flyweight}

trait CancelOrderResponse {
  def getOrderId: Long
  def isSuccess: Boolean
}

object CancelOrderResponseCodec {
  val ORDER_ID_OFFSET: Int = 0
  val ORDER_ID_SIZE: Int = ByteSize.LONG_SIZE
  val SUCCESS_OFFSET: Int = ORDER_ID_OFFSET + ORDER_ID_SIZE
  val SUCCESS_SIZE: Int = ByteSize.BOOL_SIZE
  val MESSAGE_SIZE: Int = SUCCESS_OFFSET + SUCCESS_SIZE
}

class CancelOrderResponseCodec extends CancelOrderResponse with Flyweight[CancelOrderResponseCodec] {
  import CancelOrderResponseCodec._

  private var buffer: ByteBuffer = _
  private var offset: Int = _

  override def assign(buffer: ByteBuffer, offset: Int, length: Int): CancelOrderResponseCodec = {
    if (length < MESSAGE_SIZE) {
      throw new IllegalArgumentException(s"Length must be at least $MESSAGE_SIZE")
    }
    this.buffer = buffer
    this.offset = offset
    this
  }

  def withOrderId(orderId: Long): CancelOrderResponseCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + ORDER_ID_OFFSET, orderId)
    this
  }

  def withSuccess(success: Boolean): CancelOrderResponseCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setBoolean(offset + SUCCESS_OFFSET, success)
    this
  }

  override def getOrderId: Long = buffer.getLong(offset + ORDER_ID_OFFSET)

  override def isSuccess: Boolean = buffer.getBoolean(offset + SUCCESS_OFFSET)
}
