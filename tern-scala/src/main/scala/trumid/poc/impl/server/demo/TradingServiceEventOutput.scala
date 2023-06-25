package trumid.poc.impl.server.demo

import trumid.poc.common.message._
import trumid.poc.example.TradingEngineResponsePublisher
import trumid.poc.example.commands._

class TradingServiceEventOutput(header: MessageHeader, publisher: Publisher) {
  private val client = new TradingEngineResponsePublisher(publisher.consume())

  def onPlaceOrderSuccess(command: PlaceOrderCommand) = {
    client.placeOrderResponse(header,
      _.time(command.time())
        .success(true)
    )
  }
}
