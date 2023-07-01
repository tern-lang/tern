// Generated (StructCodec)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object PlaceOrderCommandCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 40
   val TOTAL_SIZE: Int = 40
}

final class PlaceOrderCommandCodec(variable: Boolean = true) extends PlaceOrderCommandBuilder with Flyweight[PlaceOrderCommandCodec] {
   private val orderCodec: OrderInfoCodec = new OrderInfoCodec(variable) // 24
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): PlaceOrderCommandCodec = {
      val required = if(variable) PlaceOrderCommandCodec.REQUIRED_SIZE else PlaceOrderCommandCodec.TOTAL_SIZE

      if(length < required) {
         throw new IllegalArgumentException("Length is " + length + " but must be at least " + required);
      }
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.required = required;
      this;
   }

   override def instrumentId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 0)
   }

   override def instrumentId(instrumentId: Int): PlaceOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 0, instrumentId)
      this
   }

   override def order(): OrderInfo = {
      // StructGenerator
      this.buffer.setCount(this.offset + this.required);
      this.orderCodec.assign(this.buffer, this.offset + 4, this.length - 4)
   }

   override def order(order: (OrderInfoBuilder) => Unit): PlaceOrderCommandBuilder = {
      // StructGenerator
      this.buffer.setCount(this.offset + this.required);
      order.apply(this.orderCodec.assign(this.buffer, this.offset + 4, this.length - 4).defaults())
      this
   }

   override def time(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 28)
   }

   override def time(time: Long): PlaceOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 28, time)
      this
   }

   override def userId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 36)
   }

   override def userId(userId: Int): PlaceOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 36, userId)
      this
   }

   override def defaults(): PlaceOrderCommandCodec = {
      orderCodec.defaults()
      this
   }

   override def clear(): PlaceOrderCommandCodec = {
      orderCodec.clear()
      this
   }

   override def reset(): PlaceOrderCommandCodec = {
      orderCodec.reset()
      this
   }

   override def validate(): ResultCode = {
      PlaceOrderCommandValidator.validate(this)
   }
}
