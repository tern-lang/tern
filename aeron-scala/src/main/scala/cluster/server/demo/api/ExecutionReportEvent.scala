package cluster.server.demo.api

import cluster.server.message.{ByteBuffer, ByteSize, Flyweight}

trait ExecutionReportEvent {
  def getOrderId: Long
  def getQuantity: Long
  def getTime: Long
}

object ExecutionReportEventCodec {
  val ORDER_ID_OFFSET: Int = 0
  val ORDER_ID_SIZE: Int = ByteSize.LONG_SIZE
  val QUANTITY_OFFSET: Int = ORDER_ID_OFFSET + ORDER_ID_SIZE
  val QUANTITY_SIZE: Int = ByteSize.LONG_SIZE
  val TIME_OFFSET: Int = QUANTITY_OFFSET + QUANTITY_SIZE
  val TIME_SIZE: Int = ByteSize.LONG_SIZE
  val MESSAGE_SIZE: Int = TIME_OFFSET + TIME_SIZE
}

class ExecutionReportEventCodec extends ExecutionReportEvent with Flyweight[ExecutionReportEventCodec] {
  import ExecutionReportEventCodec._

  private var buffer: ByteBuffer = _
  private var offset: Int = _

  override def assign(buffer: ByteBuffer, offset: Int, length: Int): ExecutionReportEventCodec = {
    if (length < MESSAGE_SIZE) {
      throw new IllegalArgumentException(s"Length must be at least $MESSAGE_SIZE")
    }
    this.buffer = buffer
    this.offset = offset
    this
  }

  def withOrderId(orderId: Long): ExecutionReportEventCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + ORDER_ID_OFFSET, orderId)
    this
  }

  def withQuantity(quantity: Long): ExecutionReportEventCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + QUANTITY_OFFSET, quantity)
    this
  }

  def withTime(quantity: Long): ExecutionReportEventCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + TIME_OFFSET, quantity)
    this
  }

  override def getOrderId: Long = buffer.getLong(offset + ORDER_ID_OFFSET)

  override def getQuantity: Long = buffer.getLong(offset + QUANTITY_OFFSET)

  override def getTime: Long = buffer.getLong(offset + TIME_OFFSET)
}
