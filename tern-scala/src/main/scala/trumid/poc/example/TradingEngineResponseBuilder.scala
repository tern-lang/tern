// Generated at Sat Jul 01 13:00:12 BST 2023 (ServiceBuilder)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._

trait TradingEngineResponseBuilder extends TradingEngineResponse {
   override def cancelAllOrdersResponse(): CancelAllOrdersResponseBuilder
   override def cancelOrderResponse(): CancelOrderResponseBuilder
   override def createInstrumentResponse(): CreateInstrumentResponseBuilder
   override def placeOrderResponse(): PlaceOrderResponseBuilder
   override def subscribeOrderBookResponse(): OrderBookUpdateEventBuilder
   def defaults(): TradingEngineResponseBuilder
   def clear(): TradingEngineResponseBuilder
}
