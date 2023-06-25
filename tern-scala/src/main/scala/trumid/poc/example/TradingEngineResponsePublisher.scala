// Generated at Sun Jun 25 17:46:15 BST 2023 (ServicePublisher)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._

final class TradingEngineResponsePublisher(consumer: MessageConsumer[TradingEngineResponseCodec]) {
   private val composer = new TopicMessageComposer[TradingEngineResponseCodec](
      new TradingEngineResponseCodec(true),
      DirectByteBuffer(),
      11,
      0)

   def cancelAllOrdersResponse(header: MessageHeader, builder: (CancelAllOrdersResponseCodec) => Unit): Unit = {
      val cancelAllOrdersResponse = this.composer.compose().cancelAllOrdersResponse()

      try {
         builder.apply(cancelAllOrdersResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         cancelAllOrdersResponse.reset()
      }
   }

   def cancelOrderResponse(header: MessageHeader, builder: (CancelOrderResponseCodec) => Unit): Unit = {
      val cancelOrderResponse = this.composer.compose().cancelOrderResponse()

      try {
         builder.apply(cancelOrderResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         cancelOrderResponse.reset()
      }
   }

   def placeOrderResponse(header: MessageHeader, builder: (PlaceOrderResponseCodec) => Unit): Unit = {
      val placeOrderResponse = this.composer.compose().placeOrderResponse()

      try {
         builder.apply(placeOrderResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         placeOrderResponse.reset()
      }
   }
}
