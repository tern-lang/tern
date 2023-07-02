// Generated (ServiceCodec)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common._
import trumid.poc.common.topic._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object TradingEngineCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 98
   val TOTAL_SIZE: Int = 98
   val HEADER_SIZE: Int = 1
   val CANCEL_ALL_ORDERS_ID: Byte = 1
   val CANCEL_ORDER_ID: Byte = 2
   val CREATE_INSTRUMENT_ID: Byte = 3
   val PLACE_ORDER_ID: Byte = 4
   val SUBSCRIBE_EXECUTION_REPORT_ID: Byte = 5
   val SUBSCRIBE_ORDER_BOOK_ID: Byte = 6
}

final class TradingEngineCodec(variable: Boolean = true) extends TradingEngineBuilder with Flyweight[TradingEngineCodec] {
   private val cancelAllOrdersCodec: CancelAllOrdersCommandCodec = new CancelAllOrdersCommandCodec(variable) // 16
   private val cancelOrderCodec: CancelOrderCommandCodec = new CancelOrderCommandCodec(variable) // 24
   private val createInstrumentCodec: CreateInstrumentCommandCodec = new CreateInstrumentCommandCodec(variable) // 8
   private val placeOrderCodec: PlaceOrderCommandCodec = new PlaceOrderCommandCodec(variable) // 42
   private val subscribeExecutionReportCodec: ExecutionReportSubscribeCommandCodec = new ExecutionReportSubscribeCommandCodec(variable) // 4
   private val subscribeOrderBookCodec: OrderBookSubscribeCommandCodec = new OrderBookSubscribeCommandCodec(variable) // 4
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): TradingEngineCodec = {
      val required = if(variable) TradingEngineCodec.REQUIRED_SIZE else TradingEngineCodec.TOTAL_SIZE

      if(length < required) {
         throw new IllegalArgumentException("Length is " + length + " but must be at least " + required);
      }
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.required = required;
      this;
   }

   override def topic(publisher: Publisher): TopicRoute = {
      Topic(10, "TradingEngine").route((frame, payload) => {
         publisher.publish(
            frame.getFrame.getBuffer,
            frame.getFrame.getOffset,
            frame.getFrame.getLength)
      })
   }

   override def topic(handler: TradingEngineHandler): TopicRoute = {
      Topic(10, "TradingEngine").route((frame, payload) => {
         assign(
            payload.getBuffer,
            payload.getOffset,
            payload.getLength).handle(handler)
      })
   }

   override def complete(scheduler: CompletionScheduler): TopicCompletionHandler = {
      Topic(10, "TradingEngine").complete(this, (header) => {
         val code = buffer.getByte(offset)

         code match {
            case TradingEngineCodec.CANCEL_ALL_ORDERS_ID => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.complete(this.cancelAllOrdersCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE))
            }
            case TradingEngineCodec.CANCEL_ORDER_ID => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.complete(this.cancelOrderCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE))
            }
            case TradingEngineCodec.CREATE_INSTRUMENT_ID => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.complete(this.createInstrumentCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE))
            }
            case TradingEngineCodec.PLACE_ORDER_ID => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.complete(this.placeOrderCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE))
            }
            case TradingEngineCodec.SUBSCRIBE_EXECUTION_REPORT_ID => {
               val completion = scheduler.done(code, 10)
               completion.complete(this.subscribeExecutionReportCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE))
            }
            case TradingEngineCodec.SUBSCRIBE_ORDER_BOOK_ID => {
               val completion = scheduler.done(code, 10)
               completion.complete(this.subscribeOrderBookCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE))
            }
            case _ => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.failure(new IllegalStateException("Invalid code " + code))
            }
         }
      })
   }

   override def handle(handler: TradingEngineHandler): Boolean = {
      val code = buffer.getByte(offset)

      code match {
         case TradingEngineCodec.CANCEL_ALL_ORDERS_ID => {
            this.cancelAllOrdersCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onCancelAllOrders(this.cancelAllOrdersCodec)
            true
         }
         case TradingEngineCodec.CANCEL_ORDER_ID => {
            this.cancelOrderCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onCancelOrder(this.cancelOrderCodec)
            true
         }
         case TradingEngineCodec.CREATE_INSTRUMENT_ID => {
            this.createInstrumentCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onCreateInstrument(this.createInstrumentCodec)
            true
         }
         case TradingEngineCodec.PLACE_ORDER_ID => {
            this.placeOrderCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onPlaceOrder(this.placeOrderCodec)
            true
         }
         case TradingEngineCodec.SUBSCRIBE_EXECUTION_REPORT_ID => {
            this.subscribeExecutionReportCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onSubscribeExecutionReport(this.subscribeExecutionReportCodec)
            true
         }
         case TradingEngineCodec.SUBSCRIBE_ORDER_BOOK_ID => {
            this.subscribeOrderBookCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onSubscribeOrderBook(this.subscribeOrderBookCodec)
            true
         }
         case _ => {
            false
         }
      }
   }

   override def cancelAllOrders(): CancelAllOrdersCommandCodec = {
      this.buffer.setByte(this.offset, TradingEngineCodec.CANCEL_ALL_ORDERS_ID)
      this.buffer.setCount(this.offset + TradingEngineCodec.HEADER_SIZE + this.required)
      this.cancelAllOrdersCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isCancelAllOrders(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.CANCEL_ALL_ORDERS_ID
   }

   override def cancelOrder(): CancelOrderCommandCodec = {
      this.buffer.setByte(this.offset, TradingEngineCodec.CANCEL_ORDER_ID)
      this.buffer.setCount(this.offset + TradingEngineCodec.HEADER_SIZE + this.required)
      this.cancelOrderCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isCancelOrder(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.CANCEL_ORDER_ID
   }

   override def createInstrument(): CreateInstrumentCommandCodec = {
      this.buffer.setByte(this.offset, TradingEngineCodec.CREATE_INSTRUMENT_ID)
      this.buffer.setCount(this.offset + TradingEngineCodec.HEADER_SIZE + this.required)
      this.createInstrumentCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isCreateInstrument(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.CREATE_INSTRUMENT_ID
   }

   override def placeOrder(): PlaceOrderCommandCodec = {
      this.buffer.setByte(this.offset, TradingEngineCodec.PLACE_ORDER_ID)
      this.buffer.setCount(this.offset + TradingEngineCodec.HEADER_SIZE + this.required)
      this.placeOrderCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isPlaceOrder(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.PLACE_ORDER_ID
   }

   override def subscribeExecutionReport(): ExecutionReportSubscribeCommandCodec = {
      this.buffer.setByte(this.offset, TradingEngineCodec.SUBSCRIBE_EXECUTION_REPORT_ID)
      this.buffer.setCount(this.offset + TradingEngineCodec.HEADER_SIZE + this.required)
      this.subscribeExecutionReportCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isSubscribeExecutionReport(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.SUBSCRIBE_EXECUTION_REPORT_ID
   }

   override def subscribeOrderBook(): OrderBookSubscribeCommandCodec = {
      this.buffer.setByte(this.offset, TradingEngineCodec.SUBSCRIBE_ORDER_BOOK_ID)
      this.buffer.setCount(this.offset + TradingEngineCodec.HEADER_SIZE + this.required)
      this.subscribeOrderBookCodec.reset().assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isSubscribeOrderBook(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.SUBSCRIBE_ORDER_BOOK_ID
   }


   override def defaults(): TradingEngineCodec = {
      this.buffer.setByte(this.offset, 0)
      this
   }

   override def reset(): TradingEngineCodec = {
      cancelAllOrdersCodec.reset()
      cancelOrderCodec.reset()
      createInstrumentCodec.reset()
      placeOrderCodec.reset()
      subscribeExecutionReportCodec.reset()
      subscribeOrderBookCodec.reset()
      this
   }

   override def clear(): TradingEngineCodec = {
      this.buffer.setByte(this.offset, 0)
      this
   }

   override def validate(): ResultCode = {
      val code = buffer.getByte(offset)

      code match {
         case TradingEngineCodec.CANCEL_ALL_ORDERS_ID => {
            this.cancelAllOrders().validate()
         }
         case TradingEngineCodec.CANCEL_ORDER_ID => {
            this.cancelOrder().validate()
         }
         case TradingEngineCodec.CREATE_INSTRUMENT_ID => {
            this.createInstrument().validate()
         }
         case TradingEngineCodec.PLACE_ORDER_ID => {
            this.placeOrder().validate()
         }
         case TradingEngineCodec.SUBSCRIBE_EXECUTION_REPORT_ID => {
            this.subscribeExecutionReport().validate()
         }
         case TradingEngineCodec.SUBSCRIBE_ORDER_BOOK_ID => {
            this.subscribeOrderBook().validate()
         }
         case _ => {
            ResultCode.fail("Code not supported")
         }
      }
   }
}
