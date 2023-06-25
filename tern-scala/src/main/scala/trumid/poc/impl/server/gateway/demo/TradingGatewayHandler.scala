package trumid.poc.impl.server.gateway.demo

import trumid.poc.common.message._
import trumid.poc.example._
import trumid.poc.example.commands._

class TradingGatewayHandler(header: MessageHeader, publisher: TradingEnginePublisher) extends TradingEngineHandler {

  override def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    println("onPlaceOrder userId="+header.getUserId + " corr="+header.getCorrelationId + " order="+command.order().orderId().length())
    println(command.order().symbol().length())
    println(command.order().symbol())
    println(command.order().symbol().charAt(0))
    println(command.order().price())
    println(command.order().quantity())
    publisher.placeOrder(header,
      _.userId(command.userId())
        .accountId(command.accountId())
        .time(System.nanoTime())
        .order(
          _.orderId(command.order().orderId())
            .symbol(command.order().symbol())
            .price(command.order().price())
            .quantity(command.order().quantity())
        )
    )
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    println("onCancelOrder")
    publisher.cancelOrder(header,
      _.userId(command.userId())
        .accountId(command.accountId())
        .time(System.nanoTime())
        .orderId(command.orderId())
    )
  }

  override def onCancelAllOrders(command: CancelAllOrdersCommand): Unit = {
    println("onCancelAllOrders")
    publisher.cancelAllOrders(header,
      _.userId(command.userId())
        .accountId(command.accountId())
        .time(System.nanoTime())
    )
  }
}
