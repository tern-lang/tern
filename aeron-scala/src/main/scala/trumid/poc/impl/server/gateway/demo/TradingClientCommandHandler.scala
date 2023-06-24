package trumid.poc.impl.server.gateway.demo

import trumid.poc.impl.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

class TradingClientCommandHandler(client: TradingClient) {

  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    //println(s"TradingClientCommandHandler.onPlaceOrder orderId=${command.getOrderId} instrumentId=${command.getInstrumentId} time=${command.getTime}")
    client.placeOrder(command.getOrderId, command.getInstrumentId, command.getQuantity, command.getPrice, command.getTime)
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    //println(s"TradingClientCommandHandler.onCancelOrder orderId=${command.getOrderId}")
  }
}
