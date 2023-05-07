package cluster.server.demo

import cluster.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

class MatchingEngine(output: MatchingEngineOutput) {
  var count = 0

  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    val currentTime = System.currentTimeMillis()
    val sendTime = command.getTime
    val duration = currentTime - sendTime
    count += 1
    if(count % 1000 == 0) {
      println(s"MatchingEngine.onPlaceOrder orderId=${command.getOrderId} instrumentId=${command.getInstrumentId} duration=${duration}")
    }
    output.onPlaceOrderSuccess(command)
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    //println(s"onCancelOrder.onCancelOrder orderId=${command.getOrderId}")
    output.onCancelOrderSuccess(command)
  }
}
