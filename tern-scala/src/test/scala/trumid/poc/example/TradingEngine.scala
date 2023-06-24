// Generated at Sat Jun 24 14:37:07 BST 2023 (ServiceTrait)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait TradingEngine {
   def cancelAllOrders(): CancelAllOrdersCommand
   def isCancelAllOrders(): Boolean
   def cancelOrder(): CancelOrderCommand
   def isCancelOrder(): Boolean
   def placeOrder(): PlaceOrderCommand
   def isPlaceOrder(): Boolean
   def handle(handler: TradingEngineHandler): Boolean
   def validate(): ResultCode
}
