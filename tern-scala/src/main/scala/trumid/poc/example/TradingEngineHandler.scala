// Generated at Sun Jun 25 16:31:14 BST 2023 (ServiceHandler)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait TradingEngineHandler {
   def onCancelAllOrders(cancelAllOrders: CancelAllOrdersCommand): Unit
   def onCancelOrder(cancelOrder: CancelOrderCommand): Unit
   def onPlaceOrder(placeOrder: PlaceOrderCommand): Unit
}
