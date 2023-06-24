package trumid.poc.common

import trumid.poc.common.message.DirectByteBuffer
import trumid.poc.example.commands.{CancelAllOrdersCommand, CancelOrderCommand, PlaceOrderCommand}
import trumid.poc.example.{TradingEngineCodec, TradingEngineHandler}

object TradingEngineCodecTest extends App {
  val buffer = DirectByteBuffer()
  val codec = new TradingEngineCodec(true)

  codec.assign(buffer, 0, 8192)
    .defaults()
    .placeOrder()
    .userId(1)
    .accountId(Some(2))
    .order(order => {
      order.price(11.2)
        .quantity(1000)
        .orderId("ABC")
        .symbol("")
    })

  if (!codec.isPlaceOrder()) {
    throw new RuntimeException()
  }
  println(codec.validate())
  codec.handle(new MockTradingEngineHandler(value => {
    value match {
      case command: PlaceOrderCommand =>
        if(command.order().price() != 11.2) {
          throw new RuntimeException("Invalid price")
        }
        if(command.order().quantity() != 1000) {
          throw new RuntimeException("Invalid quantity")
        }
      case _ => throw new RuntimeException("No match")
    }
  }))

  class MockTradingEngineHandler(validate: (Any) => Unit) extends TradingEngineHandler {

    override def onCancelAllOrders(cancelAllOrders: CancelAllOrdersCommand): Unit = {
      validate(cancelAllOrders)
    }

    override def onCancelOrder(cancelOrder: CancelOrderCommand): Unit = {
      validate(cancelOrder)
    }

    override def onPlaceOrder(placeOrder: PlaceOrderCommand): Unit = {
      validate(placeOrder)
    }
  }
}
