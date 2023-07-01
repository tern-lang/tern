// Generated at Sat Jul 01 15:12:09 BST 2023 (StructCodec)
package trumid.poc.example.events

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object ExecutionReportSubscribeCommandCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 4
   val TOTAL_SIZE: Int = 4
}

final class ExecutionReportSubscribeCommandCodec(variable: Boolean = true) extends ExecutionReportSubscribeCommandBuilder with Flyweight[ExecutionReportSubscribeCommandCodec] {
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): ExecutionReportSubscribeCommandCodec = {
      val required = if(variable) ExecutionReportSubscribeCommandCodec.REQUIRED_SIZE else ExecutionReportSubscribeCommandCodec.TOTAL_SIZE

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

   override def instrumentId(instrumentId: Int): ExecutionReportSubscribeCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 0, instrumentId)
      this
   }

   override def defaults(): ExecutionReportSubscribeCommandCodec = {
      this
   }

   override def clear(): ExecutionReportSubscribeCommandCodec = {
      this
   }

   override def reset(): ExecutionReportSubscribeCommandCodec = {
      this
   }

   override def validate(): ResultCode = {
      ExecutionReportSubscribeCommandValidator.validate(this)
   }
}
