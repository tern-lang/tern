// Generated at Sat Jul 01 15:12:09 BST 2023 (ServiceTrait)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.common.topic._
import trumid.poc.common.message._
import trumid.poc.cluster.ResultCode

trait TradingEngineResponse {
   def cancelAllOrdersResponse(): CancelAllOrdersResponse
   def isCancelAllOrdersResponse(): Boolean
   def cancelOrderResponse(): CancelOrderResponse
   def isCancelOrderResponse(): Boolean
   def createInstrumentResponse(): CreateInstrumentResponse
   def isCreateInstrumentResponse(): Boolean
   def placeOrderResponse(): PlaceOrderResponse
   def isPlaceOrderResponse(): Boolean
   def subscribeExecutionReportResponse(): ExecutionReportEvent
   def isSubscribeExecutionReportResponse(): Boolean
   def subscribeOrderBookResponse(): OrderBookUpdateEvent
   def isSubscribeOrderBookResponse(): Boolean
   def topic(publisher: Publisher): TopicRoute
   def topic(handler: TradingEngineResponseHandler): TopicRoute
   def complete(scheduler: CompletionScheduler): TopicCompletionHandler
   def handle(handler: TradingEngineResponseHandler): Boolean
   def validate(): ResultCode
}
