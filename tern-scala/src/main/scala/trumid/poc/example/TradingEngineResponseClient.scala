// Generated at Sat Jul 01 13:00:12 BST 2023 (ServiceClient)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._
import java.util.concurrent.atomic._
import scala.concurrent._

final class TradingEngineResponseClient(consumer: MessageConsumer[TradingEngineResponseCodec], scheduler: CompletionScheduler) {
   private val counter = new AtomicLong(1)
   private val composer = new TopicMessageComposer[TradingEngineResponseCodec](
      new TradingEngineResponseCodec(true),
      DirectByteBuffer(),
      11,
      0)
}
