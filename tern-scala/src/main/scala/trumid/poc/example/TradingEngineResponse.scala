// Generated at Sat Jun 24 19:11:13 BST 2023 (ServiceTrait)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.common.topic._
import trumid.poc.cluster.ResultCode

trait TradingEngineResponse {
   def cancelAllOrdersResponse(): CancelAllOrdersResponse
   def isCancelAllOrdersResponse(): Boolean
   def cancelOrderResponse(): CancelOrderResponse
   def isCancelOrderResponse(): Boolean
   def placeOrderResponse(): PlaceOrderResponse
   def isPlaceOrderResponse(): Boolean
   def topic(handler: TradingEngineResponseHandler): TopicRoute
   def handle(handler: TradingEngineResponseHandler): Boolean
   def validate(): ResultCode
}
