package trumid.poc.impl.server.gateway.demo

import trumid.poc.example.commands._
import trumid.poc.example.{TradingEngineClient, TradingEngineHandler}

class TradingClientCommandHandler(client: TradingEngineClient) extends TradingEngineHandler {

  override def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    //println(s"TradingClientCommandHandler.onPlaceOrder orderId=${command.getOrderId} instrumentId=${command.getInstrumentId} time=${command.getTime}")
    //client.placeOrder(command.getOrderId, command.getInstrumentId, command.getQuantity, command.getPrice, command.getTime)
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    //println(s"TradingClientCommandHandler.onCancelOrder orderId=${command.getOrderId}")
  }

  override def onCancelAllOrders(cancelAllOrders: CancelAllOrdersCommand): Unit = {

  }
}
