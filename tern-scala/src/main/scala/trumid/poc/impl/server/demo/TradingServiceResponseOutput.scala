package trumid.poc.impl.server.demo

import trumid.poc.common.message._
import trumid.poc.example.TradingEngineResponsePublisher
import trumid.poc.example.commands._

class TradingServiceResponseOutput(header: MessageHeader, publisher: Publisher) {
  private val client = new TradingEngineResponsePublisher(publisher.consume())

  def onCreateInstrumentSuccess(command: CreateInstrumentCommand) = {
    client.createInstrumentResponse(header,
      _.success(true)
    )
  }

  def onCreateInstrumentFailure(command: CreateInstrumentCommand) = {
    client.createInstrumentResponse(header,
      _.success(false)
    )
  }

  def onPlaceOrderSuccess(command: PlaceOrderCommand) = {
    client.placeOrderResponse(header,
      _.time(command.time())
        .success(true)
    )
  }

  def onPlaceOrderFailure(command: PlaceOrderCommand) = {
    client.placeOrderResponse(header,
      _.time(command.time())
        .success(false)
    )
  }

  def onCancelOrderSuccess(command: CancelOrderCommand) = {
    client.cancelOrderResponse(header,
      _.time(command.time())
        .success(true)
    )
  }

  def onCancelOrderFailure(command: CancelOrderCommand) = {
    client.cancelOrderResponse(header,
      _.time(command.time())
        .success(false)
    )
  }

  def onCancelAllOrdersSuccess(command: CancelAllOrdersCommand) = {
    client.cancelAllOrdersResponse(header,
      _.time(command.time())
        .success(true)
    )
  }

  def onCancelAllOrdersFailure(command: CancelAllOrdersCommand) = {
    client.cancelAllOrdersResponse(header,
      _.time(command.time())
        .success(false)
    )
  }
}
