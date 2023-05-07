package cluster.server.demo

import cluster.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

class MatchingEngine(output: MatchingEngineOutput) {

  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    println("MatchingEngine.onPlaceOrder")
    output.onPlaceOrderSuccess(command)
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    println("MatchingEngine.onCancelOrder")
    output.onCancelOrderSuccess(command)
  }
}
