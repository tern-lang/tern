// Generated at Sat Jul 01 13:00:12 BST 2023 (ServiceClient)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
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
      val correlationId = (this.counter.getAndIncrement() * 1000) + 10
      this.scheduler.call(correlationId, 5000, completion => {
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
      val correlationId = (this.counter.getAndIncrement() * 1000) + 10
      this.scheduler.call(correlationId, 5000, completion => {
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

   def createInstrument(builder: (CreateInstrumentCommandBuilder) => Unit): Call[CreateInstrumentResponse] = {
      val correlationId = (this.counter.getAndIncrement() * 1000) + 10
      this.scheduler.call(correlationId, 5000, completion => {
         val createInstrument = this.composer.compose().createInstrument()

         try {
            builder.apply(createInstrument)
            this.composer.commit(this.consumer, 1, correlationId)
         } catch {
            case cause: Throwable => {
               completion.failure(cause)
            }
         } finally {
            createInstrument.reset()
         }
      })
   }

   def placeOrder(builder: (PlaceOrderCommandBuilder) => Unit): Call[PlaceOrderResponse] = {
      val correlationId = (this.counter.getAndIncrement() * 1000) + 10
      this.scheduler.call(correlationId, 5000, completion => {
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

   def subscribeOrderBook(builder: (OrderBookSubscribeCommandBuilder) => Unit): Stream[OrderBookUpdateEvent] = {
      val correlationId = (this.counter.getAndIncrement() * 1000) + 10
      this.scheduler.stream(correlationId, 500000, completion => {
         val subscribeOrderBook = this.composer.compose().subscribeOrderBook()

         try {
            builder.apply(subscribeOrderBook)
            this.composer.commit(this.consumer, 1, correlationId)
         } catch {
            case cause: Throwable => {
               completion.failure(cause)
            }
         } finally {
            subscribeOrderBook.reset()
         }
      })
   }
}
