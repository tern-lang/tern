// Generated at Sat Jun 24 16:49:17 BST 2023 (StructCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object OrderInfoCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 28
   val TOTAL_SIZE: Int = 28
}

final class OrderInfoCodec(variable: Boolean = true) extends OrderInfoBuilder with Flyweight[OrderInfoCodec] {
   private val orderIdCodec: CharArrayCodec = new CharArrayCodec() // (0 * (2 + 2)) + 2 + 4
   private val symbolCodec: CharArrayCodec = new CharArrayCodec() // (0 * (2 + 2)) + 2 + 4
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

   override def price(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 6)
   }

   override def price(price: Double): OrderInfoBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 6, price)
      this
   }

   override def quantity(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 14)
   }

   override def quantity(quantity: Double): OrderInfoBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 14, quantity)
      this
   }

   override def symbol(): CharArray = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.symbolCodec.assign(
         this.buffer,
         this.offset + (22 + this.buffer.getByte(this.offset + 22 + 1)),
         this.length - (22 + this.buffer.getByte(this.offset + 22 + 1))
      )
   }

   override def symbol(symbol: CharSequence): OrderInfoBuilder = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.symbolCodec.assign(this.buffer, this.offset + 22, this.length - 22)
            .clear()
            .append(symbol)
      this
   }

   override def defaults(): OrderInfoCodec = {
      this
   }

   override def clear(): OrderInfoCodec = {
      this
   }

   override def validate(): ResultCode = {
      OrderInfoValidator.validate(this)
   }
}
