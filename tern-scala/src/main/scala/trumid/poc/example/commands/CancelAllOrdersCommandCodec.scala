// Generated at Sun Jun 25 13:27:11 BST 2023 (StructCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object CancelAllOrdersCommandCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 21
   val TOTAL_SIZE: Int = 21
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

   override def accountId(): Option[Int] = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required)
      if (this.buffer.getBoolean(this.offset + this.offset + 0)) {
         Some(this.buffer.getInt(this.offset + 0))
      } else {
         None
      }
   }

   override def accountId(accountId: Option[Int]): CancelAllOrdersCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required)
      this.buffer.setBoolean(this.offset + this.offset + 0, accountId.isDefined)
      if (accountId.isDefined) this.buffer.setInt(this.offset + this.offset + 0, accountId.get)
      this
   }

   override def time(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 9)
   }

   override def time(time: Long): CancelAllOrdersCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 9, time)
      this
   }

   override def userId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 17)
   }

   override def userId(userId: Int): CancelAllOrdersCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 17, userId)
      this
   }

   override def defaults(): CancelAllOrdersCommandCodec = {
      this
   }

   override def clear(): CancelAllOrdersCommandCodec = {
      this
   }

   override def validate(): ResultCode = {
      CancelAllOrdersCommandValidator.validate(this)
   }
}
