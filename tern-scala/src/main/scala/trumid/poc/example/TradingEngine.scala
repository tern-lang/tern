// Generated (ServiceTrait)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.common.topic._
import trumid.poc.common.message._
import trumid.poc.cluster.ResultCode

trait TradingEngine {
   def cancelAllOrders(): CancelAllOrdersCommand
   def isCancelAllOrders(): Boolean
   def cancelOrder(): CancelOrderCommand
   def isCancelOrder(): Boolean
   def createInstrument(): CreateInstrumentCommand
   def isCreateInstrument(): Boolean
   def placeOrder(): PlaceOrderCommand
   def isPlaceOrder(): Boolean
   def subscribeExecutionReport(): ExecutionReportSubscribeCommand
   def isSubscribeExecutionReport(): Boolean
   def subscribeOrderBook(): OrderBookSubscribeCommand
   def isSubscribeOrderBook(): Boolean
   def topic(publisher: Publisher): TopicRoute
   def topic(handler: TradingEngineHandler): TopicRoute
   def complete(scheduler: CompletionScheduler): TopicCompletionHandler
   def handle(handler: TradingEngineHandler): Boolean
   def validate(): ResultCode
}
