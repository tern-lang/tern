// Generated at Sat Jul 01 13:00:12 BST 2023 (ServiceHandler)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait TradingEngineResponseHandler {
   def onCancelAllOrdersResponse(cancelAllOrdersResponse: CancelAllOrdersResponse): Unit
   def onCancelOrderResponse(cancelOrderResponse: CancelOrderResponse): Unit
   def onCreateInstrumentResponse(createInstrumentResponse: CreateInstrumentResponse): Unit
   def onPlaceOrderResponse(placeOrderResponse: PlaceOrderResponse): Unit
   def onSubscribeOrderBookResponse(subscribeOrderBookResponse: OrderBookUpdateEvent): Unit
}
