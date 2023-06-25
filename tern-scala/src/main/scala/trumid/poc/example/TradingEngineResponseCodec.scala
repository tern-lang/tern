// Generated at Sun Jun 25 16:31:14 BST 2023 (ServiceCodec)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.topic._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object TradingEngineResponseCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 60
   val TOTAL_SIZE: Int = 60
   val HEADER_SIZE: Int = 1
   val CANCEL_ALL_ORDERS_RESPONSE_ID: Byte = 1
   val CANCEL_ORDER_RESPONSE_ID: Byte = 2
   val PLACE_ORDER_RESPONSE_ID: Byte = 3
}

final class TradingEngineResponseCodec(variable: Boolean = true) extends TradingEngineResponseBuilder with Flyweight[TradingEngineResponseCodec] {
   private val cancelAllOrdersResponseCodec: CancelAllOrdersResponseCodec = new CancelAllOrdersResponseCodec(variable) // 20
   private val cancelOrderResponseCodec: CancelOrderResponseCodec = new CancelOrderResponseCodec(variable) // 20
   private val placeOrderResponseCodec: PlaceOrderResponseCodec = new PlaceOrderResponseCodec(variable) // 20
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
            frame.getBody.getBuffer,
            frame.getBody.getOffset,
            frame.getBody.getLength)
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
         val correlationId = header.getCorrelationId
         val completion = scheduler.stop(correlationId)

         if(completion != null) {
            val code = buffer.getByte(offset)

            code match {
               case TradingEngineResponseCodec.CANCEL_ALL_ORDERS_RESPONSE_ID => {
                  completion.complete(this.cancelAllOrdersResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
               }
               case TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID => {
                  completion.complete(this.cancelOrderResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
               }
               case TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID => {
                  completion.complete(this.placeOrderResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE))
               }
               case _ => {
                  completion.failure(new IllegalStateException("Invalid code " + code))
               }
            }
         }
      })
   }

   override def handle(handler: TradingEngineResponseHandler): Boolean = {
      val code = buffer.getByte(offset)

      code match {
         case TradingEngineResponseCodec.CANCEL_ALL_ORDERS_RESPONSE_ID => {
            this.cancelAllOrdersResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onCancelAllOrdersResponse(this.cancelAllOrdersResponseCodec)
            true
         }
         case TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID => {
            this.cancelOrderResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onCancelOrderResponse(this.cancelOrderResponseCodec)
            true
         }
         case TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID => {
            this.placeOrderResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
            handler.onPlaceOrderResponse(this.placeOrderResponseCodec)
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
      this.cancelAllOrdersResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isCancelAllOrdersResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.CANCEL_ALL_ORDERS_RESPONSE_ID
   }

   override def cancelOrderResponse(): CancelOrderResponseCodec = {
      this.buffer.setByte(this.offset, TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID)
      this.buffer.setCount(this.offset + TradingEngineResponseCodec.HEADER_SIZE + this.required)
      this.cancelOrderResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isCancelOrderResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.CANCEL_ORDER_RESPONSE_ID
   }

   override def placeOrderResponse(): PlaceOrderResponseCodec = {
      this.buffer.setByte(this.offset, TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID)
      this.buffer.setCount(this.offset + TradingEngineResponseCodec.HEADER_SIZE + this.required)
      this.placeOrderResponseCodec.assign(this.buffer, this.offset + TradingEngineResponseCodec.HEADER_SIZE, this.length - TradingEngineResponseCodec.HEADER_SIZE)
   }

   override def isPlaceOrderResponse(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID
   }


   override def defaults(): TradingEngineResponseCodec = {
      this.buffer.setByte(this.offset, 0)
      this
   }

   override def reset(): TradingEngineResponseCodec = {
      cancelAllOrdersResponseCodec.reset()
      cancelOrderResponseCodec.reset()
      placeOrderResponseCodec.reset()
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
         case TradingEngineResponseCodec.PLACE_ORDER_RESPONSE_ID => {
            this.placeOrderResponse().validate()
         }
         case _ => {
            ResultCode.fail("Code not supported")
         }
      }
   }
}
