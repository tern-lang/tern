package cluster.server.gateway.demo

import cluster.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

class TradingClientCommandHandler {
  
  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    println("onPlaceOrder")
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    println("onCancelOrder")
  }
}
