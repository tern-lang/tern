// Generated at Sat Jun 17 19:39:26 BST 2023 (StructCodec)
package trumid.poc.example

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object UserCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 8
   val TOTAL_SIZE: Int = 8
}

final class UserCodec(variable: Boolean = true) extends UserBuilder with Flyweight[UserCodec] {
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): UserCodec = {
      val required = if(variable) UserCodec.REQUIRED_SIZE else UserCodec.TOTAL_SIZE

      if(length < required) {
         throw new IllegalArgumentException("Length is " + length + " but must be at least " + required);
      }
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.required = required;
      this;
   }

   override def accountId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 0)
   }

   override def accountId(accountId: Int): UserBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 0, accountId)
      this
   }

   override def userId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 4)
   }

   override def userId(userId: Int): UserBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 4, userId)
      this
   }

   override def defaults(): UserCodec = {
      this
   }

   override def clear(): UserCodec = {
      this
   }

   override def validate(): ResultCode = {
      UserValidator.validate(this)
   }
}
