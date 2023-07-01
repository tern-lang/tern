// Generated at Sat Jul 01 13:00:12 BST 2023 (ServiceBuilder)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._

trait TradingEngineBuilder extends TradingEngine {
   override def cancelAllOrders(): CancelAllOrdersCommandBuilder
   override def cancelOrder(): CancelOrderCommandBuilder
   override def createInstrument(): CreateInstrumentCommandBuilder
   override def placeOrder(): PlaceOrderCommandBuilder
   override def subscribeOrderBook(): OrderBookSubscribeCommandBuilder
   def defaults(): TradingEngineBuilder
   def clear(): TradingEngineBuilder
}
