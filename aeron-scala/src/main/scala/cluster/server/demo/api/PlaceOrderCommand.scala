package cluster.server.demo.api

import cluster.server.message.{ByteBuffer, ByteSize, Flyweight}

trait PlaceOrderCommand {
  def getOrderId: Long
  def getInstrumentId: Int
  def getQuantity: Long
  def getPrice: Double
  def getTime: Long
}

object PlaceOrderCommandCodec {
  val ORDER_ID_OFFSET: Int = 0
  val ORDER_ID_SIZE: Int = ByteSize.LONG_SIZE
  val INSTRUMENT_ID_OFFSET: Int = ORDER_ID_OFFSET + ORDER_ID_SIZE
  val INSTRUMENT_ID_SIZE: Int = ByteSize.INT_SIZE
  val QUANTITY_OFFSET: Int = INSTRUMENT_ID_OFFSET + INSTRUMENT_ID_SIZE
  val QUANTITY_SIZE: Int = ByteSize.LONG_SIZE
  val PRICE_OFFSET: Int = QUANTITY_OFFSET + QUANTITY_SIZE
  val PRICE_SIZE: Int = ByteSize.DOUBLE_SIZE
  val TIME_OFFSET: Int = PRICE_OFFSET + PRICE_SIZE
  val TIME_SIZE: Int = ByteSize.LONG_SIZE
  val MESSAGE_SIZE: Int = TIME_OFFSET + TIME_SIZE
}

class PlaceOrderCommandCodec extends PlaceOrderCommand with Flyweight[PlaceOrderCommandCodec] {
  import PlaceOrderCommandCodec._

  private var buffer: ByteBuffer = _
  private var offset: Int = _

  override def assign(buffer: ByteBuffer, offset: Int, length: Int): PlaceOrderCommandCodec = {
    if (length < MESSAGE_SIZE) {
      throw new IllegalArgumentException(s"Length must be at least $MESSAGE_SIZE")
    }
    this.buffer = buffer
    this.offset = offset
    this
  }

  def withOrderId(orderId: Long): PlaceOrderCommandCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + ORDER_ID_OFFSET, orderId)
    this
  }

  def withInstrumentId(instrumentId: Int): PlaceOrderCommandCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setInt(offset + INSTRUMENT_ID_OFFSET, instrumentId)
    this
  }

  def withQuantity(quantity: Long): PlaceOrderCommandCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + QUANTITY_OFFSET, quantity)
    this
  }

  def withPrice(price: Double): PlaceOrderCommandCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setDouble(offset + PRICE_OFFSET, price)
    this
  }

  def withTime(time: Long): PlaceOrderCommandCodec = {
    buffer.setCount(offset + MESSAGE_SIZE)
    buffer.setLong(offset + TIME_OFFSET, time)
    this
  }

  override def getInstrumentId: Int = buffer.getInt(offset + INSTRUMENT_ID_OFFSET)

  override def getQuantity: Long = buffer.getLong(offset + QUANTITY_OFFSET)

  override def getPrice: Double = buffer.getDouble(offset + PRICE_OFFSET)

  override def getOrderId: Long = buffer.getLong(offset + ORDER_ID_OFFSET)

  override def getTime: Long = buffer.getLong(offset + TIME_OFFSET)
}

