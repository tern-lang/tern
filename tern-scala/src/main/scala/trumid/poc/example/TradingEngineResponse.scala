// Generated at Sat Jun 24 16:49:17 BST 2023 (ServiceTrait)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait TradingEngineResponse {
   def cancelAllOrdersResponse(): CancelAllOrdersResponse
   def isCancelAllOrdersResponse(): Boolean
   def cancelOrderResponse(): CancelOrderResponse
   def isCancelOrderResponse(): Boolean
   def placeOrderResponse(): PlaceOrderResponse
   def isPlaceOrderResponse(): Boolean
   def handle(handler: TradingEngineResponseHandler): Boolean
   def validate(): ResultCode
}
