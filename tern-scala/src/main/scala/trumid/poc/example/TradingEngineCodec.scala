// Generated at Sat Jun 24 19:11:13 BST 2023 (ServiceCodec)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.topic._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object TradingEngineCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 97
   val TOTAL_SIZE: Int = 97
   val HEADER_SIZE: Int = 1
   val CANCEL_ALL_ORDERS_ID: Byte = 1
   val CANCEL_ORDER_ID: Byte = 2
   val PLACE_ORDER_ID: Byte = 3
}

final class TradingEngineCodec(variable: Boolean = true) extends TradingEngineBuilder with Flyweight[TradingEngineCodec] {
   private val cancelAllOrdersCodec: CancelAllOrdersCommandCodec = new CancelAllOrdersCommandCodec(variable) // 21
   private val cancelOrderCodec: CancelOrderCommandCodec = new CancelOrderCommandCodec(variable) // 27
   private val placeOrderCodec: PlaceOrderCommandCodec = new PlaceOrderCommandCodec(variable) // 49
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

   override def topic(handler: TradingEngineHandler): TopicRoute = {
      Topic(10, "TradingEngine").route((frame, payload) => {
         assign(
            payload.getBuffer,
            payload.getOffset,
            payload.getLength).handle(handler)
      })
   }

   override def handle(handler: TradingEngineHandler): Boolean = {
      val code = buffer.getByte(offset)

      code match {
         case TradingEngineCodec.CANCEL_ALL_ORDERS_ID => {
            this.cancelAllOrdersCodec.assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onCancelAllOrders(this.cancelAllOrdersCodec)
            true
         }
         case TradingEngineCodec.CANCEL_ORDER_ID => {
            this.cancelOrderCodec.assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onCancelOrder(this.cancelOrderCodec)
            true
         }
         case TradingEngineCodec.PLACE_ORDER_ID => {
            this.placeOrderCodec.assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
            handler.onPlaceOrder(this.placeOrderCodec)
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
      this.cancelAllOrdersCodec.assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isCancelAllOrders(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.CANCEL_ALL_ORDERS_ID
   }

   override def cancelOrder(): CancelOrderCommandCodec = {
      this.buffer.setByte(this.offset, TradingEngineCodec.CANCEL_ORDER_ID)
      this.buffer.setCount(this.offset + TradingEngineCodec.HEADER_SIZE + this.required)
      this.cancelOrderCodec.assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isCancelOrder(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.CANCEL_ORDER_ID
   }

   override def placeOrder(): PlaceOrderCommandCodec = {
      this.buffer.setByte(this.offset, TradingEngineCodec.PLACE_ORDER_ID)
      this.buffer.setCount(this.offset + TradingEngineCodec.HEADER_SIZE + this.required)
      this.placeOrderCodec.assign(this.buffer, this.offset + TradingEngineCodec.HEADER_SIZE, this.length - TradingEngineCodec.HEADER_SIZE)
   }

   override def isPlaceOrder(): Boolean = {
      this.buffer.getByte(this.offset) == TradingEngineCodec.PLACE_ORDER_ID
   }


   override def defaults(): TradingEngineCodec = {
      this.buffer.setByte(this.offset, 0)
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
         case TradingEngineCodec.PLACE_ORDER_ID => {
            this.placeOrder().validate()
         }
         case _ => {
            ResultCode.fail("Code not supported")
         }
      }
   }
}
