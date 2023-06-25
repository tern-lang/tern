// Generated at Sun Jun 25 13:27:11 BST 2023 (ServiceBuilder)
package trumid.poc.example

import trumid.poc.example.commands._

trait TradingEngineResponseBuilder extends TradingEngineResponse {
   override def cancelAllOrdersResponse(): CancelAllOrdersResponseBuilder
   override def cancelOrderResponse(): CancelOrderResponseBuilder
   override def placeOrderResponse(): PlaceOrderResponseBuilder
   def defaults(): TradingEngineResponseBuilder
   def clear(): TradingEngineResponseBuilder
}
