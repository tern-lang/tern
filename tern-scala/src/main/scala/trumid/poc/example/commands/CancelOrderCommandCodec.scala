// Generated at Sun Jun 25 12:15:27 BST 2023 (StructCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object CancelOrderCommandCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 27
   val TOTAL_SIZE: Int = 27
}

final class CancelOrderCommandCodec(variable: Boolean = true) extends CancelOrderCommandBuilder with Flyweight[CancelOrderCommandCodec] {
   private val orderIdCodec: CharArrayCodec = new CharArrayCodec() // (0 * (2 + 2)) + 2 + 4
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): CancelOrderCommandCodec = {
      val required = if(variable) CancelOrderCommandCodec.REQUIRED_SIZE else CancelOrderCommandCodec.TOTAL_SIZE

      if(length < required) {
         throw new IllegalArgumentException("Length is " + length + " but must be at least " + required);
      }
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.required = required;
      this;
   }

   override def accountId(): Option[Int] = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required)
      if (this.buffer.getBoolean(this.offset + this.offset + 0)) {
         Some(this.buffer.getInt(this.offset + 0))
      } else {
         None
      }
   }

   override def accountId(accountId: Option[Int]): CancelOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required)
      this.buffer.setBoolean(this.offset + this.offset + 0, accountId.isDefined)
      if (accountId.isDefined) this.buffer.setInt(this.offset + this.offset + 0, accountId.get)
      this
   }

   override def orderId(): CharArray = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.orderIdCodec.assign(
         this.buffer,
         this.offset + (9 + this.buffer.getByte(this.offset + 9 + 1)),
         this.length - (9 + this.buffer.getByte(this.offset + 9 + 1))
      )
   }

   override def orderId(orderId: CharSequence): CancelOrderCommandBuilder = {
      // PrimitiveArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.orderIdCodec.assign(this.buffer, this.offset + 9, this.length - 9)
            .clear()
            .append(orderId)
      this
   }

   override def time(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 15)
   }

   override def time(time: Long): CancelOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 15, time)
      this
   }

   override def userId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 23)
   }

   override def userId(userId: Int): CancelOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 23, userId)
      this
   }

   override def defaults(): CancelOrderCommandCodec = {
      this
   }

   override def clear(): CancelOrderCommandCodec = {
      this
   }

   override def validate(): ResultCode = {
      CancelOrderCommandValidator.validate(this)
   }
}
