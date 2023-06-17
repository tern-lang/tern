// Generated at Sat Jun 17 21:18:13 BST 2023 (ServiceTrait)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait TradingEngine {
   def cancelAllOrders(): CancelAllOrdersCommand
   def ifCancelAllOrders(cancelAllOrders: (CancelAllOrdersCommand) => Unit): Unit
   def cancelOrder(): CancelOrderCommand
   def ifCancelOrder(cancelOrder: (CancelOrderCommand) => Unit): Unit
   def placeOrder(): PlaceOrderCommand
   def ifPlaceOrder(placeOrder: (PlaceOrderCommand) => Unit): Unit
   def handle(handler: TradingEngineHandler): Boolean
   def validate(): ResultCode
}
