package cluster.server.gateway.demo

import cluster.server.demo.api.{CancelOrderResponse, ExecutionReportEvent, PlaceOrderResponse}

class TradingClientResponseHandler {

  def onPlaceOrderResponse(response: PlaceOrderResponse) = {
    println(s"TradingClientResponseHandler.onPlaceOrderResponse ${response.getOrderId}")
  }

  def onCancelOrderResponse(response: CancelOrderResponse) = {
    println(s"TradingClientResponseHandler.onCancelOrderResponse ${response.getOrderId}")
  }

  def onExecutionReport(report: ExecutionReportEvent) =  {
    println(s"TradingClientResponseHandler.onExecutionReport ${report.getOrderId}")
  }

}
