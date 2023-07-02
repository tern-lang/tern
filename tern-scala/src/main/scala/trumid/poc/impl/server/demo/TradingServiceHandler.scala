package trumid.poc.impl.server.demo

import org.agrona.collections.Int2ObjectHashMap
import trumid.poc.example.TradingEngineHandler
import trumid.poc.example.commands._
import trumid.poc.example.events.{ExecutionReportSubscribeCommand, OrderBookSubscribeCommand}
import trumid.poc.impl.server.demo.book._

class TradingServiceHandler(response: TradingServiceResponseOutput, event: TradingServiceEventOutput) extends TradingEngineHandler {
  private val orderBooks = new Int2ObjectHashMap[OrderBook]()

  override def onCreateInstrument(command: CreateInstrumentCommand): Unit = {
    val orderBook = TradingServiceHandler.newOrderBook(command, event)

    orderBooks.put(orderBook.instrument.instrumentId, orderBook)
    response.onCreateInstrumentSuccess(command)
  }

  override def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    val orderBook = orderBooks.get(command.instrumentId)
    val order = TradingServiceHandler.newOrder(command, orderBook.instrument())

    orderBook.placeOrder(order)
    response.onPlaceOrderSuccess(command)
    event.onComplete(command.instrumentId())
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    val orderBook = orderBooks.get(command.instrumentId())

    orderBook.removeOrder(command.orderId())
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

    orderBook.activeOrders(Side.BUY).forEachRemaining(event.onPassive)
    orderBook.activeOrders(Side.SELL).forEachRemaining(event.onPassive)
    event.onComplete(command.instrumentId())
  }

  override def onSubscribeExecutionReport(command: ExecutionReportSubscribeCommand): Unit = {
    event.onComplete(command.instrumentId())
  }
}

object TradingServiceHandler {

  def newOrderBook(command: CreateInstrumentCommand, channel: OrderChannel): OrderBook = {
    val instrumentId = command.instrumentId()
    val scale = PriceScale(command.scale())
    val instrument = Instrument(instrumentId, scale)

    new OrderBook(instrument, channel)
  }

  def newOrder(command: PlaceOrderCommand, instrument: Instrument): trumid.poc.impl.server.demo.book.Order = {
    val order = command.order()
    val orderId = order.orderId()
    val userId = command.userId()
    val side = order.side()
    val orderType = order.orderType()
    val price = instrument.scale.toPrice(order.price())
    val quantity = order.quantity()

    new trumid.poc.impl.server.demo.book.Order(userId, orderId, side, orderType, price, quantity)
  }
}