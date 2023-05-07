package cluster.server.demo

import cluster.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

class MatchingEngine(output: MatchingEngineOutput) {

  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    println(s"MatchingEngine.onPlaceOrder orderId=${command.getOrderId} instrumentId=${command.getInstrumentId}")
    output.onPlaceOrderSuccess(command)
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    println(s"onCancelOrder.onCancelOrder orderId=${command.getOrderId}")
    output.onCancelOrderSuccess(command)
  }
}
