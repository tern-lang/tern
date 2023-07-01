// Generated at Sat Jul 01 15:12:09 BST 2023 (StructCodec)
package trumid.poc.example.events

import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object ExecutionReportEventCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 20
   val TOTAL_SIZE: Int = 20
}

final class ExecutionReportEventCodec(variable: Boolean = true) extends ExecutionReportEventBuilder with Flyweight[ExecutionReportEventCodec] {
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): ExecutionReportEventCodec = {
      val required = if(variable) ExecutionReportEventCodec.REQUIRED_SIZE else ExecutionReportEventCodec.TOTAL_SIZE

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

   override def instrumentId(instrumentId: Int): ExecutionReportEventBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 0, instrumentId)
      this
   }

   override def price(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 4)
   }

   override def price(price: Double): ExecutionReportEventBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 4, price)
      this
   }

   override def quantity(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 12)
   }

   override def quantity(quantity: Double): ExecutionReportEventBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 12, quantity)
      this
   }

   override def defaults(): ExecutionReportEventCodec = {
      this
   }

   override def clear(): ExecutionReportEventCodec = {
      this
   }

   override def reset(): ExecutionReportEventCodec = {
      this
   }

   override def validate(): ResultCode = {
      ExecutionReportEventValidator.validate(this)
   }
}
