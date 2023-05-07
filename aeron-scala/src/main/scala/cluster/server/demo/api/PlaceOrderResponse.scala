package cluster.server.demo.api


import cluster.server.message.{ByteBuffer, ByteSize, Flyweight}

trait PlaceOrderResponse {
  def getOrderId: Long
  def isSuccess: Boolean
}

object PlaceOrderResponseCodec {
  val ORDER_ID_OFFSET: Int = 0
  val ORDER_ID_SIZE: Int = ByteSize.LONG_SIZE
  val SUCCESS_OFFSET: Int = ORDER_ID_OFFSET + ORDER_ID_SIZE
  val SUCCESS_SIZE: Int = ByteSize.BOOL_SIZE
  val MESSAGE_SIZE: Int = SUCCESS_OFFSET + SUCCESS_SIZE
}

class PlaceOrderResponseCodec extends PlaceOrderResponse with Flyweight[PlaceOrderResponseCodec] {
  import PlaceOrderResponseCodec._

  private var buffer: ByteBuffer = _
  private var offset: Int = _

  override def assign(buffer: ByteBuffer, offset: Int, length: Int): PlaceOrderResponseCodec = {
    if (length < MESSAGE_SIZE) {
      throw new IllegalArgumentException(s"Length must be at least $MESSAGE_SIZE")
    }
    this.buffer = buffer
    this.offset = offset
    this
  }

  def withOrderId(orderId: Long): PlaceOrderResponseCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + ORDER_ID_OFFSET, orderId)
    this
  }

  def withSuccess(success: Boolean): PlaceOrderResponseCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setBoolean(offset + SUCCESS_OFFSET, success)
    this
  }

  override def getOrderId: Long = buffer.getLong(offset + ORDER_ID_OFFSET)

  override def isSuccess: Boolean = buffer.getBoolean(offset + SUCCESS_OFFSET)
}
