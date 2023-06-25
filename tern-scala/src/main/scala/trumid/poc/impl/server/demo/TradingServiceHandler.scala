package trumid.poc.impl.server.demo

import trumid.poc.example.TradingEngineHandler
import trumid.poc.example.commands._

class TradingServiceHandler(output: TradingServiceOutput) extends TradingEngineHandler {

  override def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    println("onPlaceOrder")
    output.onPlaceOrderSuccess(command)
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    output.onCancelOrderSuccess(command)
  }

  override def onCancelAllOrders(command: CancelAllOrdersCommand): Unit = {
    output.onCancelAllOrdersSuccess(command)
  }
}
