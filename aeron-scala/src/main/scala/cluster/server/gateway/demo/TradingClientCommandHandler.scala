package cluster.server.gateway.demo

import cluster.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

class TradingClientCommandHandler(client: TradingClient) {

  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    println(s"TradingClientCommandHandler.onPlaceOrder orderId=${command.getOrderId} instrumentId=${command.getInstrumentId}")
    client.placeOrder(command.getOrderId, command.getInstrumentId, command.getQuantity, command.getPrice)
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    println(s"TradingClientCommandHandler.onCancelOrder orderId=${command.getOrderId}")
  }
}
