package trumid.poc.impl.server.gateway.demo

import trumid.poc.common.message._
import trumid.poc.example._
import trumid.poc.example.commands._

class TradingGatewayHandler(header: MessageHeader, publisher: TradingEnginePublisher) extends TradingEngineHandler {

  override def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    publisher.placeOrder(header,
      _.userId(command.userId())
        .accountId(command.accountId())
        .time(command.time())
        .order(
          _.orderId(command.order().orderId())
            .symbol(command.order().symbol())
            .price(command.order().price())
            .quantity(command.order().quantity())
        )
    )
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    publisher.cancelOrder(header,
      _.userId(command.userId())
        .accountId(command.accountId())
        .time(command.time())
        .orderId(command.orderId())
    )
  }

  override def onCancelAllOrders(command: CancelAllOrdersCommand): Unit = {
    publisher.cancelAllOrders(header,
      _.userId(command.userId())
        .accountId(command.accountId())
        .time(command.time())
    )
  }
}
