// Generated at Sat Jul 01 15:12:09 BST 2023 (ServiceHandler)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait TradingEngineHandler {
   def onCancelAllOrders(cancelAllOrders: CancelAllOrdersCommand): Unit
   def onCancelOrder(cancelOrder: CancelOrderCommand): Unit
   def onCreateInstrument(createInstrument: CreateInstrumentCommand): Unit
   def onPlaceOrder(placeOrder: PlaceOrderCommand): Unit
   def onSubscribeExecutionReport(subscribeExecutionReport: ExecutionReportSubscribeCommand): Unit
   def onSubscribeOrderBook(subscribeOrderBook: OrderBookSubscribeCommand): Unit
}
