// Generated at Sun Jun 25 12:15:27 BST 2023 (ServicePublisher)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._

final class TradingEngineResponsePublisher(consumer: MessageConsumer[TradingEngineResponseCodec]) {
   private val composer = new TopicMessageComposer[TradingEngineResponseCodec](
      new TradingEngineResponseCodec(true),
      DirectByteBuffer(),
      0,
      0)

   def cancelAllOrdersResponse(header: MessageHeader, builder: (CancelAllOrdersResponseCodec) => Unit): Unit = {
      builder.apply(this.composer.compose().cancelAllOrdersResponse())
      this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
   }

   def cancelOrderResponse(header: MessageHeader, builder: (CancelOrderResponseCodec) => Unit): Unit = {
      builder.apply(this.composer.compose().cancelOrderResponse())
      this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
   }

   def placeOrderResponse(header: MessageHeader, builder: (PlaceOrderResponseCodec) => Unit): Unit = {
      builder.apply(this.composer.compose().placeOrderResponse())
      this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
   }
}
