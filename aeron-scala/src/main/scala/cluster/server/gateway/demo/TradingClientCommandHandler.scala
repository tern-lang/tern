package cluster.server.gateway.demo

import cluster.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

class TradingClientCommandHandler(client: TradingClient) {

  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    println("onPlaceOrder")
    client.placeOrder(command.getOrderId, command.getInstrumentId, command.getQuantity, command.getPrice)
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    println("onCancelOrder")
  }
}
