// Generated at Sun Jun 25 13:27:11 BST 2023 (ServiceClient)
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

   def cancelAllOrders(builder: (CancelAllOrdersCommandBuilder) => Unit): Call[CancelAllOrdersResponse] = {
      val correlationId = this.counter.getAndIncrement()
      this.scheduler.start(correlationId, 5000, completion => {
         try {
            builder.apply(this.composer.compose().cancelAllOrders())
            this.composer.commit(this.consumer, 1, correlationId)
         } catch {
            case cause: Throwable => {
               completion.failure(cause)
            }
         }
      })
   }

   def cancelOrder(builder: (CancelOrderCommandBuilder) => Unit): Call[CancelOrderResponse] = {
      val correlationId = this.counter.getAndIncrement()
      this.scheduler.start(correlationId, 5000, completion => {
         try {
            builder.apply(this.composer.compose().cancelOrder())
            this.composer.commit(this.consumer, 1, correlationId)
         } catch {
            case cause: Throwable => {
               completion.failure(cause)
            }
         }
      })
   }

   def placeOrder(builder: (PlaceOrderCommandBuilder) => Unit): Call[PlaceOrderResponse] = {
      val correlationId = this.counter.getAndIncrement()
      this.scheduler.start(correlationId, 5000, completion => {
         try {
            builder.apply(this.composer.compose().placeOrder())
            this.composer.commit(this.consumer, 1, correlationId)
         } catch {
            case cause: Throwable => {
               completion.failure(cause)
            }
         }
      })
   }
}
