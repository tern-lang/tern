package cluster.server.demo

import cluster.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

class MatchingEngine {

  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    println("MatchingEngine.onPlaceOrder")
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    println("MatchingEngine.onCancelOrder")
  }
}
