package trumid.poc.impl.server.demo

import trumid.poc.common.message._
import trumid.poc.example.TradingEngineResponseClient
import trumid.poc.example.commands._

class MatchingEngineOutput(publisher: Publisher) {
  private val client: TradingEngineResponseClient = new TradingEngineResponseClient(publisher.consume())

  def onPlaceOrderSuccess(command: PlaceOrderCommand) = {
    client.publish(1,
      _.placeOrderResponse()
        .success(true)
    )
  }

  def onPlaceOrderFailure(command: PlaceOrderCommand) = {
    client.publish(1,
      _.placeOrderResponse()
        .success(false)
    )
  }

  def onCancelOrderSuccess(command: CancelOrderCommand) = {
    client.publish(1,
      _.cancelOrderResponse()
        .success(true)
    )
  }

  def onCancelOrderFailure(command: CancelOrderCommand) = {
    client.publish(1,
      _.cancelOrderResponse()
        .success(false)
    )
  }
}
