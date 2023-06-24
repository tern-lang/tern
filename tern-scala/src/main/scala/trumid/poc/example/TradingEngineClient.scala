// Generated at Sat Jun 24 16:49:17 BST 2023 (ServiceClient)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._

final class TradingEngineClient(consumer: MessageConsumer[TradingEngineCodec]) {
   private val composer = new TopicMessageComposer[TradingEngineCodec](
      new TradingEngineCodec(true),
      DirectByteBuffer(),
      0,
      0
   )

   def publish(correlationId: Long, builder: (TradingEngineCodec) => Unit): Unit = {
      builder.apply(this.composer.compose())
      this.composer.commit(this.consumer, 1, correlationId)
   }
}
