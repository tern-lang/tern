// Generated at Sat Jun 17 21:18:13 BST 2023 (ServiceBuilder)
package trumid.poc.example

import trumid.poc.example.commands._

trait TradingEngineBuilder extends TradingEngine {
   override def cancelAllOrders(): CancelAllOrdersCommandBuilder
   override def ifCancelAllOrders(cancelAllOrders: (CancelAllOrdersCommand) => Unit): Unit
   override def cancelOrder(): CancelOrderCommandBuilder
   override def ifCancelOrder(cancelOrder: (CancelOrderCommand) => Unit): Unit
   override def placeOrder(): PlaceOrderCommandBuilder
   override def ifPlaceOrder(placeOrder: (PlaceOrderCommand) => Unit): Unit
   def defaults(): TradingEngineBuilder
   def clear(): TradingEngineBuilder
}
