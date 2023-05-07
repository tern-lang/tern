package cluster.server.gateway.demo

import cluster.server.demo.api.{CancelOrderResponse, ExecutionReportEvent, PlaceOrderResponse}

class TradingClientResponseHandler {

  def onPlaceOrderResponse(response: PlaceOrderResponse) = {
    println("onPlaceOrderResponse")
  }

  def onCancelOrderResponse(response: CancelOrderResponse) = {
    println("onCancelOrderResponse")
  }

  def onExecutionReport(report: ExecutionReportEvent) =  {
    println("onExecutionReport")
  }

}
