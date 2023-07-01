// Generated (StructCodec)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object OrderInfoCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 24
   val TOTAL_SIZE: Int = 24
}

final class OrderInfoCodec(variable: Boolean = true) extends OrderInfoBuilder with Flyweight[OrderInfoCodec] {
   private val orderIdCodec: CharArrayCodec = new CharArrayCodec() // (0 * (2 + 2)) + 2 + 4
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): OrderInfoCodec = {
      val required = if(variable) OrderInfoCodec.REQUIRED_SIZE else OrderInfoCodec.TOTAL_SIZE

      if(length < required) {
         throw new IllegalArgumentException("Length is " + length + " but must be at least " + required);
      }
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.required = required;
      this;
   }

   override def orderId(): CharArray = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.orderIdCodec.assign(
         this.buffer,
         this.offset + (0 + this.buffer.getByte(this.offset + 0 + 1)),
         this.length - (0 + this.buffer.getByte(this.offset + 0 + 1))
      )
   }

   override def orderId(orderId: CharSequence): OrderInfoBuilder = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.orderIdCodec.assign(this.buffer, this.offset + 0, this.length - 0)
            .clear()
            .append(orderId)
      this
   }

   override def orderType(): OrderType = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      OrderType.resolve(this.buffer.getByte(this.offset + 6))
   }

   override def orderType(orderType: OrderType): OrderInfoBuilder = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setByte(this.offset + 6, orderType.toCode)
      this
   }

   override def price(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 7)
   }

   override def price(price: Double): OrderInfoBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 7, price)
      this
   }

   override def quantity(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 15)
   }

   override def quantity(quantity: Long): OrderInfoBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 15, quantity)
      this
   }

   override def side(): Side = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      Side.resolve(this.buffer.getByte(this.offset + 23))
   }

   override def side(side: Side): OrderInfoBuilder = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setByte(this.offset + 23, side.toCode)
      this
   }

   override def defaults(): OrderInfoCodec = {
      this
   }

   override def clear(): OrderInfoCodec = {
      orderIdCodec.clear()
      this
   }

   override def reset(): OrderInfoCodec = {
      orderIdCodec.reset()
      this
   }

   override def validate(): ResultCode = {
      OrderInfoValidator.validate(this)
   }
}
