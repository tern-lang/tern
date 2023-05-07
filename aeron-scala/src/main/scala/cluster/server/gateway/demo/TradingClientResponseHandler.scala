package cluster.server.gateway.demo

import cluster.server.demo.api.{CancelOrderResponse, ExecutionReportEvent, PlaceOrderResponse}

class TradingClientResponseHandler {
  var count = 0

  def onPlaceOrderResponse(response: PlaceOrderResponse) = {
    val currentTime = System.currentTimeMillis()
    val sendTime = response.getTime
    val duration = currentTime - sendTime
    count += 1
    if(count % 1000 == 0) {
      println(s"TradingClientResponseHandler.onPlaceOrderResponse duration=${duration} ${response.getOrderId} originalTime=${sendTime} currentTime=${currentTime}")
    }
  }

  def onCancelOrderResponse(response: CancelOrderResponse) = {
    println(s"TradingClientResponseHandler.onCancelOrderResponse ${response.getOrderId}")
  }

  def onExecutionReport(report: ExecutionReportEvent) =  {
    println(s"TradingClientResponseHandler.onExecutionReport ${report.getOrderId}")
  }

}
