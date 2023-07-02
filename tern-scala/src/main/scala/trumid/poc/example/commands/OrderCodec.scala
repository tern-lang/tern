// Generated (StructCodec)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object OrderCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 26
   val TOTAL_SIZE: Int = 26
}

final class OrderCodec(variable: Boolean = true) extends OrderBuilder with Flyweight[OrderCodec] {
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): OrderCodec = {
      val required = if(variable) OrderCodec.REQUIRED_SIZE else OrderCodec.TOTAL_SIZE

      if(length < required) {
         throw new IllegalArgumentException("Length is " + length + " but must be at least " + required);
      }
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.required = required;
      this;
   }

   override def orderId(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 0)
   }

   override def orderId(orderId: Long): OrderBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 0, orderId)
      this
   }

   override def orderType(): OrderType = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      OrderType.resolve(this.buffer.getByte(this.offset + 8))
   }

   override def orderType(orderType: OrderType): OrderBuilder = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setByte(this.offset + 8, orderType.toCode)
      this
   }

   override def price(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 9)
   }

   override def price(price: Double): OrderBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 9, price)
      this
   }

   override def quantity(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 17)
   }

   override def quantity(quantity: Long): OrderBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 17, quantity)
      this
   }

   override def side(): Side = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      Side.resolve(this.buffer.getByte(this.offset + 25))
   }

   override def side(side: Side): OrderBuilder = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setByte(this.offset + 25, side.toCode)
      this
   }

   override def defaults(): OrderCodec = {
      this
   }

   override def clear(): OrderCodec = {
      this
   }

   override def reset(): OrderCodec = {
      this
   }

   override def validate(): ResultCode = {
      OrderValidator.validate(this)
   }
}
