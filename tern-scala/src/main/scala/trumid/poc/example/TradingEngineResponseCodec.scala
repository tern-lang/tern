// Generated at Sat Jul 01 15:12:09 BST 2023 (ServiceCodec)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common._
import trumid.poc.common.topic._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object TradingEngineResponseCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 108
   val TOTAL_SIZE: Int = 108
   val HEADER_SIZE: Int = 1
   val CANCEL_ALL_ORDERS_RESPONSE_ID: Byte = 1
   val CANCEL_ORDER_RESPONSE_ID: Byte = 2
   val CREATE_INSTRUMENT_RESPONSE_ID: Byte = 3
   val PLACE_ORDER_RESPONSE_ID: Byte = 4
   val SUBSCRIBE_EXECUTION_REPORT_RESPONSE_ID: Byte = 5
   val SUBSCRIBE_ORDER_BOOK_RESPONSE_ID: Byte = 6
}

final class TradingEngineResponseCodec(variable: Boolean = true) extends TradingEngineResponseBuilder with Flyweight[TradingEngineResponseCodec] {
   private val cancelAllOrdersResponseCodec: CancelAllOrdersResponseCodec = new CancelAllOrdersResponseCodec(variable) // 20
   private val cancelOrderResponseCodec: CancelOrderResponseCodec = new CancelOrderResponseCodec(variable) // 20
   private val createInstrumentResponseCodec: CreateInstrumentResponseCodec = new CreateInstrumentResponseCodec(variable) // 12
   private val placeOrderResponseCodec: PlaceOrderResponseCodec = new PlaceOrderResponseCodec(variable) // 20
   private val subscribeExecutionReportResponseCodec: ExecutionReportEventCodec = new ExecutionReportEventCodec(variable) // 20
   private val subscribeOrderBookResponseCodec: OrderBookUpdateEventCodec = new OrderBookUpdateEventCodec(variable) // 16
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): TradingEngineResponseCodec = {
      val required = if(variable) TradingEngineResponseCodec.REQUIRED_SIZE else TradingEngineResponseCodec.TOTAL_SIZE

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
      Topic(11, "TradingEngineResponse").route((frame, payload) => {
         publisher.publish(
            frame.getFrame.getBuffer,
            frame.getFrame.getOffset,
            frame.getFrame.getLength)
      })
   }

   override def topic(handler: TradingEngineResponseHandler): TopicRoute = {
      Topic(11, "TradingEngineResponse").route((frame, payload) => {
         assign(
            payload.getBuffer,
            payload.getOffset,
            payload.getLength).handle(handler)
      })
   }

   override def complete(scheduler: CompletionScheduler): TopicCompletionHandler = {
      Topic(11, "TradingEngineResponse").complete(this, (header) => {
         val code = buffer.getByte(offset)

         code match {
            case TradingEngineResponseCodec.CANCEL_ALL_ORDERS_RESPONSE_ID => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.complete(this.cancelAllOrdersResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
            }
            case TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.complete(this.cancelOrderResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
            }
            case TradingEngineResponseCodec.CREATE_INSTRUMENT_RESPONSE_ID => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.complete(this.createInstrumentResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
            }
            case TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.complete(this.placeOrderResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
            }
            case TradingEngineResponseCodec.SUBSCRIBE_EXECUTION_REPORT_RESPONSE_ID => {
               val completion = scheduler.done(code, 11)
               completion.complete(this.subscribeExecutionReportResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
            }
            case TradingEngineResponseCodec.SUBSCRIBE_ORDER_BOOK_RESPONSE_ID => {
               val completion = scheduler.done(code, 11)
               completion.complete(this.subscribeOrderBookResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
            }
            case _ => {
               val completion = scheduler.done(code, header.getCorrelationId)
               completion.failure(new IllegalStateException("Invalid code " + code))
            }
         }
      })
   }

   override def handle(handler: TradingEngineResponseHandler): Boolean = {
      val code = buffer.getByte(offset)

      code match {
         case TradingEngineResponseCodec.CANCEL_ALL_ORDERS_RESPONSE_ID => {
            this.cancelAllOrdersResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onCancelAllOrdersResponse(this.cancelAllOrdersResponseCodec)
            true
         }
         case TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID => {
            this.cancelOrderResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onCancelOrderResponse(this.cancelOrderResponseCodec)
            true
         }
         case TradingEngineResponseCodec.CREATE_INSTRUMENT_RESPONSE_ID => {
            this.createInstrumentResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onCreateInstrumentResponse(this.createInstrumentResponseCodec)
            true
         }
         case TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID => {
            this.placeOrderResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onPlaceOrderResponse(this.placeOrderResponseCodec)
            true
         }
         case TradingEngineResponseCodec.SUBSCRIBE_EXECUTION_REPORT_RESPONSE_ID => {
            this.subscribeExecutionReportResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onSubscribeExecutionReportResponse(this.subscribeExecutionReportResponseCodec)
            true
         }
         case TradingEngineResponseCodec.SUBSCRIBE_ORDER_BOOK_RESPONSE_ID => {
            this.subscribeOrderBookResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onSubscribeOrderBookResponse(this.subscribeOrderBookResponseCodec)
            true
         }
         case _ => {
            false
         }
      }
   }

   override def cancelAllOrdersResponse(): CancelAllOrdersResponseCodec = {
      this.buffer.setByte(this.offset, TradingEngineResponseCodec.CANCEL_ALL_ORDERS_RESPONSE_ID)
      this.buffer.setCount(this.offset + TradingEngineResponseCodec.HEADER_SIZE + this.required)
      this.cancelAllOrdersResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isCancelAllOrdersResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.CANCEL_ALL_ORDERS_RESPONSE_ID
   }

   override def cancelOrderResponse(): CancelOrderResponseCodec = {
      this.buffer.setByte(this.offset, TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID)
      this.buffer.setCount(this.offset + TradingEngineResponseCodec.HEADER_SIZE + this.required)
      this.cancelOrderResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isCancelOrderResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID
   }

   override def createInstrumentResponse(): CreateInstrumentResponseCodec = {
      this.buffer.setByte(this.offset, TradingEngineResponseCodec.CREATE_INSTRUMENT_RESPONSE_ID)
      this.buffer.setCount(this.offset + TradingEngineResponseCodec.HEADER_SIZE + this.required)
      this.createInstrumentResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isCreateInstrumentResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.CREATE_INSTRUMENT_RESPONSE_ID
   }

   override def placeOrderResponse(): PlaceOrderResponseCodec = {
      this.buffer.setByte(this.offset, TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID)
      this.buffer.setCount(this.offset + TradingEngineResponseCodec.HEADER_SIZE + this.required)
      this.placeOrderResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isPlaceOrderResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID
   }

   override def subscribeExecutionReportResponse(): ExecutionReportEventCodec = {
      this.buffer.setByte(this.offset, TradingEngineResponseCodec.SUBSCRIBE_EXECUTION_REPORT_RESPONSE_ID)
      this.buffer.setCount(this.offset + TradingEngineResponseCodec.HEADER_SIZE + this.required)
      this.subscribeExecutionReportResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isSubscribeExecutionReportResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.SUBSCRIBE_EXECUTION_REPORT_RESPONSE_ID
   }

   override def subscribeOrderBookResponse(): OrderBookUpdateEventCodec = {
      this.buffer.setByte(this.offset, TradingEngineResponseCodec.SUBSCRIBE_ORDER_BOOK_RESPONSE_ID)
      this.buffer.setCount(this.offset + TradingEngineResponseCodec.HEADER_SIZE + this.required)
      this.subscribeOrderBookResponseCodec.reset().assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isSubscribeOrderBookResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.SUBSCRIBE_ORDER_BOOK_RESPONSE_ID
   }


   override def defaults(): TradingEngineResponseCodec = {
      this.buffer.setByte(this.offset, 0)
      this
   }

   override def reset(): TradingEngineResponseCodec = {
      cancelAllOrdersResponseCodec.reset()
      cancelOrderResponseCodec.reset()
      createInstrumentResponseCodec.reset()
      placeOrderResponseCodec.reset()
      subscribeExecutionReportResponseCodec.reset()
      subscribeOrderBookResponseCodec.reset()
      this
   }

   override def clear(): TradingEngineResponseCodec = {
      this.buffer.setByte(this.offset, 0)
      this
   }

   override def validate(): ResultCode = {
      val code = buffer.getByte(offset)

      code match {
         case TradingEngineResponseCodec.CANCEL_ALL_ORDERS_RESPONSE_ID => {
            this.cancelAllOrdersResponse().validate()
         }
         case TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID => {
            this.cancelOrderResponse().validate()
         }
         case TradingEngineResponseCodec.CREATE_INSTRUMENT_RESPONSE_ID => {
            this.createInstrumentResponse().validate()
         }
         case TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID => {
            this.placeOrderResponse().validate()
         }
         case TradingEngineResponseCodec.SUBSCRIBE_EXECUTION_REPORT_RESPONSE_ID => {
            this.subscribeExecutionReportResponse().validate()
         }
         case TradingEngineResponseCodec.SUBSCRIBE_ORDER_BOOK_RESPONSE_ID => {
            this.subscribeOrderBookResponse().validate()
         }
         case _ => {
            ResultCode.fail("Code not supported")
         }
      }
   }
}
