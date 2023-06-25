// Generated at Sun Jun 25 12:15:27 BST 2023 (StructCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object CancelAllOrdersResponseCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 20
   val TOTAL_SIZE: Int = 20
}

final class CancelAllOrdersResponseCodec(variable: Boolean = true) extends CancelAllOrdersResponseBuilder with Flyweight[CancelAllOrdersResponseCodec] {
   private val reasonCodec: CharArrayCodec = new CharArrayCodec() // (0 * (2 + 2)) + 2 + 4+ 1 + 4
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): CancelAllOrdersResponseCodec = {
      val required = if(variable) CancelAllOrdersResponseCodec.REQUIRED_SIZE else CancelAllOrdersResponseCodec.TOTAL_SIZE

      if(length < required) {
         throw new IllegalArgumentException("Length is " + length + " but must be at least " + required);
      }
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.required = required;
      this;
   }

   override def reason(): Option[CharArray] = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required)
      if (this.buffer.getBoolean(this.offset + this.offset + 0)) {
         Some(this.reasonCodec.assign(
            this.buffer,
            this.offset + (0 + this.buffer.getByte(this.offset + 0 + 1)),
            this.length - (0 + this.buffer.getByte(this.offset + 0 + 1))
         ))
      } else {
         None
      }
   }

   override def reason(reason: Option[CharSequence]): CancelAllOrdersResponseBuilder = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required)
      this.buffer.setBoolean(this.offset + 0, reason.isDefined)
      if (reason.isDefined) {
         this.reasonCodec.assign(
            this.buffer,
            this.offset + (0 + this.buffer.getByte(this.offset + 0 + 1)),
            this.length - (0 + this.buffer.getByte(this.offset + 0 + 1))
         )
         .clear()
         .append(reason.get)
      }
      this
   }

   override def success(): Boolean = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getBoolean(this.offset + 11)
   }

   override def success(success: Boolean): CancelAllOrdersResponseBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setBoolean(this.offset + 11, success)
      this
   }

   override def time(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 12)
   }

   override def time(time: Long): CancelAllOrdersResponseBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 12, time)
      this
   }

   override def defaults(): CancelAllOrdersResponseCodec = {
      this
   }

   override def clear(): CancelAllOrdersResponseCodec = {
      this
   }

   override def validate(): ResultCode = {
      CancelAllOrdersResponseValidator.validate(this)
   }
}
