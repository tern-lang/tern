// Generated at Sun Jun 25 17:46:15 BST 2023 (ServiceBuilder)
package trumid.poc.example

import trumid.poc.example.commands._

trait TradingEngineBuilder extends TradingEngine {
   override def cancelAllOrders(): CancelAllOrdersCommandBuilder
   override def cancelOrder(): CancelOrderCommandBuilder
   override def placeOrder(): PlaceOrderCommandBuilder
   def defaults(): TradingEngineBuilder
   def clear(): TradingEngineBuilder
}
