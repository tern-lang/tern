package trumid.poc.common

import trumid.poc.common.message.DirectByteBuffer
import trumid.poc.common.topic.TopicMessageHeader
import trumid.poc.example.commands.{CancelAllOrdersCommand, CancelOrderCommand, CreateInstrumentCommand, PlaceOrderCommand}
import trumid.poc.example.events.{ExecutionReportSubscribeCommand, OrderBookSubscribeCommand}
import trumid.poc.example.{TradingEngineCodec, TradingEngineHandler}

object TradingEngineCodecTest extends App {
  val buffer = DirectByteBuffer()
  val codec = new TradingEngineCodec(true)
  val codec2 = new TradingEngineCodec(true)

  codec.assign(buffer, 0, 8192)
    .defaults()
    .placeOrder()
    .userId(1)
    .instrumentId(2)
    .order(order => {
      order.price(11.2)
        .quantity(1000)
        .orderId("Hello world!")
    })

  if (!codec.isPlaceOrder()) {
    throw new RuntimeException()
  }
  println(codec.validate())
  println(TopicMessageHeader.HEADER_SIZE)

  val handler = new MockTradingEngineHandler(value => {
    value match {
      case command: PlaceOrderCommand =>
        println("####################################")
        println(command.order().orderId())
        println(command.validate())

        if(command.order().price() != 11.2) {
          throw new RuntimeException("Invalid price")
        }
        if(command.order().quantity() != 1000) {
          throw new RuntimeException("Invalid quantity")
        }
      case _ => throw new RuntimeException("No match")
    }
  })

  codec.handle(handler)
  codec.reset()
  codec2.assign(buffer, 0, 8192)
  codec2.handle(handler)

  class MockTradingEngineHandler(validate: (Any) => Unit) extends TradingEngineHandler {

    override def onCreateInstrument(createInstrument: CreateInstrumentCommand): Unit  = {
      validate(createInstrument)
    }

    override def onCancelAllOrders(cancelAllOrders: CancelAllOrdersCommand): Unit = {
      validate(cancelAllOrders)
    }

    override def onCancelOrder(cancelOrder: CancelOrderCommand): Unit = {
      validate(cancelOrder)
    }

    override def onPlaceOrder(placeOrder: PlaceOrderCommand): Unit = {
      validate(placeOrder)
    }

    override def onSubscribeOrderBook(subscribeOrderBook: OrderBookSubscribeCommand): Unit = {
      validate(subscribeOrderBook)
    }

    override def onSubscribeExecutionReport(subscribeExecutionReport: ExecutionReportSubscribeCommand): Unit = {
      validate(subscribeExecutionReport)
    }

  }
}
