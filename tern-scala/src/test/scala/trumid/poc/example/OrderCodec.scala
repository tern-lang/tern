// Generated at Sat Jun 17 19:31:04 BST 2023 (StructCodec)
package trumid.poc.example

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object OrderCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 33
   val TOTAL_SIZE: Int = 33
}

final class OrderCodec(variable: Boolean = true) extends OrderBuilder with Flyweight[OrderCodec] {
   private val descriptionCodec: CharArrayCodec = new CharArrayCodec() // (0 * (2 + 2)) + 2 + 4+ 1 + 4
   private val symbolCodec: CharArrayCodec = new CharArrayCodec() // (0 * (2 + 2)) + 2 + 4
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

   override def description(): Option[CharArray] = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required)
      if (this.buffer.getBoolean(this.offset + this.offset + 0)) {
         Some(this.descriptionCodec.assign(
            this.buffer,
            this.offset + (0 + this.buffer.getByte(this.offset + 0 + 1)),
            this.length - (0 + this.buffer.getByte(this.offset + 0 + 1))
         ))
      } else {
         None
      }
   }

   override def description(description: Option[CharSequence]): OrderBuilder = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required)
      this.buffer.setBoolean(this.offset + 0, description.isDefined)
      if (description.isDefined) {
         this.descriptionCodec.assign(
            this.buffer,
            this.offset + (0 + this.buffer.getByte(this.offset + 0 + 1)),
            this.length - (0 + this.buffer.getByte(this.offset + 0 + 1))
         )
         .clear()
         .append(description.get)
      }
      this
   }

   override def price(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 11)
   }

   override def price(price: Double): OrderBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 11, price)
      this
   }

   override def quantity(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 19)
   }

   override def quantity(quantity: Long): OrderBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 19, quantity)
      this
   }

   override def symbol(): CharArray = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.symbolCodec.assign(
         this.buffer,
         this.offset + (27 + this.buffer.getByte(this.offset + 27 + 1)),
         this.length - (27 + this.buffer.getByte(this.offset + 27 + 1))
      )
   }

   override def symbol(symbol: CharSequence): OrderBuilder = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.symbolCodec.assign(this.buffer, this.offset + 27, this.length - 27)
            .clear()
            .append(symbol)
      this
   }

   override def defaults(): OrderCodec = {
      this
   }

   override def clear(): OrderCodec = {
      this
   }

   override def validate(): ResultCode = {
      OrderValidator.validate(this)
   }
}
