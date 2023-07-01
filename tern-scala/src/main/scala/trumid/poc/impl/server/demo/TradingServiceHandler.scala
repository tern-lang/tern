package trumid.poc.impl.server.demo

import org.agrona.collections.Int2ObjectHashMap
import trumid.poc.example.TradingEngineHandler
import trumid.poc.example.commands._
import trumid.poc.example.events.{ExecutionReportSubscribeCommand, OrderBookSubscribeCommand}
import trumid.poc.impl.server.demo.book._

class TradingServiceHandler(response: TradingServiceResponseOutput, event: TradingServiceEventOutput) extends TradingEngineHandler {
  private val orderBooks = new Int2ObjectHashMap[OrderBook]()

  override def onCreateInstrument(command: CreateInstrumentCommand): Unit = {
    val instrument = Instrument(command.instrumentId(), PriceScale(command.scale()))

    orderBooks.put(
      instrument.instrumentId,
      new OrderBook(instrument, event))
    response.onCreateInstrumentSuccess(command)
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
    event.onComplete(command.instrumentId())
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    val orderBook = orderBooks.get(command.instrumentId())

    orderBook.removeOrder(command.orderId().toString())
    response.onCancelOrderSuccess(command)
    event.onComplete(command.instrumentId())
  }

  override def onCancelAllOrders(command: CancelAllOrdersCommand): Unit = {
    val orderBook = orderBooks.get(command.instrumentId())

    orderBook.removeAllOrders(command.userId())
    response.onCancelAllOrdersSuccess(command)
    event.onComplete(command.instrumentId())
  }

  override def onSubscribeOrderBook(command: OrderBookSubscribeCommand): Unit = {
    val orderBook = orderBooks.get(command.instrumentId())

    orderBook.activeOrders(Buy).forEachRemaining(event.onPassive)
    orderBook.activeOrders(Sell).forEachRemaining(event.onPassive)
    event.onComplete(command.instrumentId())
  }

  override def onSubscribeExecutionReport(command: ExecutionReportSubscribeCommand): Unit = {
    event.onComplete(command.instrumentId())
  }
}
