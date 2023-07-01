package trumid.poc.impl.server.gateway.demo

import trumid.poc.common.message._
import trumid.poc.example._
import trumid.poc.example.commands._
import trumid.poc.example.events.{ExecutionReportSubscribeCommand, OrderBookSubscribeCommand}

class TradingGatewayHandler(header: MessageHeader, publisher: TradingEnginePublisher) extends TradingEngineHandler {

  override def onCreateInstrument(command: CreateInstrumentCommand): Unit = {
    publisher.createInstrument(header,
      _.instrumentId(command.instrumentId())
        .scale(command.scale())
    )
  }

  override def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    publisher.placeOrder(header,
      _.userId(command.userId())
        .instrumentId(command.instrumentId())
        .time(command.time())
        .order(
          _.orderId(command.order().orderId())
            .price(command.order().price())
            .quantity(command.order().quantity())
            .side(command.order().side())
            .orderType(command.order.orderType()))
    )
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    publisher.cancelOrder(header,
      _.userId(command.userId())
        .instrumentId(command.instrumentId())
        .time(command.time())
        .orderId(command.orderId())
    )
  }

  override def onCancelAllOrders(command: CancelAllOrdersCommand): Unit = {
    publisher.cancelAllOrders(header,
      _.userId(command.userId())
        .instrumentId(command.instrumentId())
        .time(command.time())
    )
  }

  override def onSubscribeOrderBook(command: OrderBookSubscribeCommand): Unit = {
    publisher.subscribeOrderBook(header,
      _.instrumentId(command.instrumentId())
    )
  }

  override def onSubscribeExecutionReport(command: ExecutionReportSubscribeCommand): Unit = {
    publisher.subscribeExecutionReport(header,
      _.instrumentId(command.instrumentId())
    )
  }
}
