// Generated at Sat Jul 01 13:00:12 BST 2023 (StructCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object CreateInstrumentCommandCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 8
   val TOTAL_SIZE: Int = 8
}

final class CreateInstrumentCommandCodec(variable: Boolean = true) extends CreateInstrumentCommandBuilder with Flyweight[CreateInstrumentCommandCodec] {
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): CreateInstrumentCommandCodec = {
      val required = if(variable) CreateInstrumentCommandCodec.REQUIRED_SIZE else CreateInstrumentCommandCodec.TOTAL_SIZE

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

   override def instrumentId(instrumentId: Int): CreateInstrumentCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 0, instrumentId)
      this
   }

   override def scale(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 4)
   }

   override def scale(scale: Int): CreateInstrumentCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 4, scale)
      this
   }

   override def defaults(): CreateInstrumentCommandCodec = {
      this
   }

   override def clear(): CreateInstrumentCommandCodec = {
      this
   }

   override def reset(): CreateInstrumentCommandCodec = {
      this
   }

   override def validate(): ResultCode = {
      CreateInstrumentCommandValidator.validate(this)
   }
}
