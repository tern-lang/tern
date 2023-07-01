// Generated (StructCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object CancelAllOrdersCommandCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 16
   val TOTAL_SIZE: Int = 16
}

final class CancelAllOrdersCommandCodec(variable: Boolean = true) extends CancelAllOrdersCommandBuilder with Flyweight[CancelAllOrdersCommandCodec] {
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): CancelAllOrdersCommandCodec = {
      val required = if(variable) CancelAllOrdersCommandCodec.REQUIRED_SIZE else CancelAllOrdersCommandCodec.TOTAL_SIZE

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

   override def instrumentId(instrumentId: Int): CancelAllOrdersCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 0, instrumentId)
      this
   }

   override def time(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 4)
   }

   override def time(time: Long): CancelAllOrdersCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 4, time)
      this
   }

   override def userId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 12)
   }

   override def userId(userId: Int): CancelAllOrdersCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 12, userId)
      this
   }

   override def defaults(): CancelAllOrdersCommandCodec = {
      this
   }

   override def clear(): CancelAllOrdersCommandCodec = {
      this
   }

   override def reset(): CancelAllOrdersCommandCodec = {
      this
   }

   override def validate(): ResultCode = {
      CancelAllOrdersCommandValidator.validate(this)
   }
}
