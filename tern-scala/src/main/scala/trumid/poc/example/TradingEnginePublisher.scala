// Generated at Sun Jun 25 16:31:14 BST 2023 (ServicePublisher)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._

final class TradingEnginePublisher(consumer: MessageConsumer[TradingEngineCodec]) {
   private val composer = new TopicMessageComposer[TradingEngineCodec](
      new TradingEngineCodec(true),
      DirectByteBuffer(),
      0,
      0)

   def cancelAllOrders(header: MessageHeader, builder: (CancelAllOrdersCommandCodec) => Unit): Unit = {
      builder.apply(this.composer.compose().cancelAllOrders())
      this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
   }

   def cancelOrder(header: MessageHeader, builder: (CancelOrderCommandCodec) => Unit): Unit = {
      builder.apply(this.composer.compose().cancelOrder())
      this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
   }

   def placeOrder(header: MessageHeader, builder: (PlaceOrderCommandCodec) => Unit): Unit = {
      builder.apply(this.composer.compose().placeOrder())
      this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
   }
}
