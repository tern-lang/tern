// Generated at Sun Jun 25 12:15:27 BST 2023 (ServiceClient)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._
import java.util.concurrent.atomic._
import scala.concurrent._

final class TradingEngineClient(consumer: MessageConsumer[TradingEngineCodec], scheduler: CompletionScheduler) {
   private val counter = new AtomicLong(1)
   private val composer = new TopicMessageComposer[TradingEngineCodec](
      new TradingEngineCodec(true),
      DirectByteBuffer(),
      10,
      0)

   def cancelAllOrders(builder: (CancelAllOrdersCommandBuilder) => Unit): Future[CancelAllOrdersResponse] = {
      val correlationId = this.counter.getAndIncrement()
      val complete = this.scheduler.start(correlationId, 5000)

      try {
         builder.apply(this.composer.compose().cancelAllOrders())
         this.composer.commit(this.consumer, 1, correlationId)
         complete.future()
      } catch {
         case cause: Throwable => {
            complete.failure(cause).future()
         }
      }
   }

   def cancelOrder(builder: (CancelOrderCommandBuilder) => Unit): Future[CancelOrderResponse] = {
      val correlationId = this.counter.getAndIncrement()
      val complete = this.scheduler.start(correlationId, 5000)

      try {
         builder.apply(this.composer.compose().cancelOrder())
         this.composer.commit(this.consumer, 1, correlationId)
         complete.future()
      } catch {
         case cause: Throwable => {
            complete.failure(cause).future()
         }
      }
   }

   def placeOrder(builder: (PlaceOrderCommandBuilder) => Unit): Future[PlaceOrderResponse] = {
      val correlationId = this.counter.getAndIncrement()
      val complete = this.scheduler.start(correlationId, 5000)

      try {
         builder.apply(this.composer.compose().placeOrder())
         this.composer.commit(this.consumer, 1, correlationId)
         complete.future()
      } catch {
         case cause: Throwable => {
            complete.failure(cause).future()
         }
      }
   }
}
