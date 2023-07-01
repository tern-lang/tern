package trumid.poc.impl.server.demo

import org.agrona.collections.Int2ObjectHashMap
import trumid.poc.example.TradingEngineHandler
import trumid.poc.example.commands._
import trumid.poc.example.events.OrderBookSubscribeCommand
import trumid.poc.impl.server.demo.book._

class TradingServiceHandler(response: TradingServiceResponseOutput, event: TradingServiceEventOutput) extends TradingEngineHandler {
  private val orderBooks = new Int2ObjectHashMap[OrderBook]()

  override def onCreateInstrument(command: CreateInstrumentCommand): Unit = {
    val instrument = Instrument(command.instrumentId(), PriceScale(command.scale()))

    orderBooks.put(
      instrument.instrumentId,
      new OrderBook(instrument, event))
  }

  override def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    val orderInfo = command.order()
    val orderBook = orderBooks.get(command.instrumentId())
    val order = new Order(
      command.userId(),
      orderInfo.orderId().toString(),
      if (orderInfo.side().isSell()) Sell else Buy,
      if (orderInfo.orderType().isLimit()) Limit else Market,
      orderBook.instrument().scale.toPrice(orderInfo.price()),
      command.order().quantity().longValue())

    orderBook.placeOrder(order)
    response.onPlaceOrderSuccess(command)
    event.onComplete()
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    val orderBook = orderBooks.get(command.instrumentId())

    orderBook.removeOrder(command.orderId().toString())
    response.onCancelOrderSuccess(command)
    event.onComplete()
  }

  override def onCancelAllOrders(command: CancelAllOrdersCommand): Unit = {
    val orderBook = orderBooks.get(command.instrumentId())

    orderBook.removeAllOrders(command.userId())
    response.onCancelAllOrdersSuccess(command)
    event.onComplete()
  }

  override def onSubscribeOrderBook(subscribeOrderBook: OrderBookSubscribeCommand): Unit = {

  }
}
