// Generated at Sat Jun 24 19:11:13 BST 2023 (ServiceClient)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._

final class TradingEngineResponseClient(consumer: MessageConsumer[TradingEngineResponseCodec]) {
   private val composer = new TopicMessageComposer[TradingEngineResponseCodec](
      new TradingEngineResponseCodec(true),
      DirectByteBuffer(),
      0,
      0
   )

   def publish(correlationId: Long, builder: (TradingEngineResponseCodec) => Unit): Unit = {
      builder.apply(this.composer.compose())
      this.composer.commit(this.consumer, 1, correlationId)
   }
}
