// Generated at Sun Jun 25 17:46:15 BST 2023 (ServiceClient)
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
         val cancelAllOrders = this.composer.compose().cancelAllOrders()

         try {
            builder.apply(cancelAllOrders)
            this.composer.commit(this.consumer, 1, correlationId)
         } catch {
            case cause: Throwable => {
               completion.failure(cause)
            }
         } finally {
            cancelAllOrders.reset()
         }
      })
   }

   def cancelOrder(builder: (CancelOrderCommandBuilder) => Unit): Call[CancelOrderResponse] = {
      val correlationId = this.counter.getAndIncrement()
      this.scheduler.start(correlationId, 5000, completion => {
         val cancelOrder = this.composer.compose().cancelOrder()

         try {
            builder.apply(cancelOrder)
            this.composer.commit(this.consumer, 1, correlationId)
         } catch {
            case cause: Throwable => {
               completion.failure(cause)
            }
         } finally {
            cancelOrder.reset()
         }
      })
   }

   def placeOrder(builder: (PlaceOrderCommandBuilder) => Unit): Call[PlaceOrderResponse] = {
      val correlationId = this.counter.getAndIncrement()
      this.scheduler.start(correlationId, 5000, completion => {
         val placeOrder = this.composer.compose().placeOrder()

         try {
            builder.apply(placeOrder)
            this.composer.commit(this.consumer, 1, correlationId)
         } catch {
            case cause: Throwable => {
               completion.failure(cause)
            }
         } finally {
            placeOrder.reset()
         }
      })
   }
}
