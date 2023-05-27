package cluster.server.gateway.demo

import cluster.server.demo.api.{CancelOrderResponse, ExecutionReportEvent, PlaceOrderResponse}

import java.util.concurrent.TimeUnit

class TradingClientResponseHandler {
  var count = 0

  def onPlaceOrderResponse(response: PlaceOrderResponse) = {
    val currentTime = TimeUnit.NANOSECONDS.toMicros(System.nanoTime())
    val sendTime = response.getTime
    val duration = currentTime - sendTime
    count += 1
    if(count % 20000 == 0) {
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
