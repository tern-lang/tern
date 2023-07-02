// Generated (StructCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object CancelOrderCommandCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 24
   val TOTAL_SIZE: Int = 24
}

final class CancelOrderCommandCodec(variable: Boolean = true) extends CancelOrderCommandBuilder with Flyweight[CancelOrderCommandCodec] {
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

   override def instrumentId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 0)
   }

   override def instrumentId(instrumentId: Int): CancelOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 0, instrumentId)
      this
   }

   override def orderId(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 4)
   }

   override def orderId(orderId: Long): CancelOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 4, orderId)
      this
   }

   override def time(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 12)
   }

   override def time(time: Long): CancelOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 12, time)
      this
   }

   override def userId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 20)
   }

   override def userId(userId: Int): CancelOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 20, userId)
      this
   }

   override def defaults(): CancelOrderCommandCodec = {
      this
   }

   override def clear(): CancelOrderCommandCodec = {
      this
   }

   override def reset(): CancelOrderCommandCodec = {
      this
   }

   override def validate(): ResultCode = {
      CancelOrderCommandValidator.validate(this)
   }
}
