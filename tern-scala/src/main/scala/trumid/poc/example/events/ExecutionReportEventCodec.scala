// Generated (StructCodec)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object ExecutionReportEventCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 21
   val TOTAL_SIZE: Int = 21
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

   override def fillType(): FillType = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      FillType.resolve(this.buffer.getByte(this.offset + 0))
   }

   override def fillType(fillType: FillType): ExecutionReportEventBuilder = {
      // EnumGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setByte(this.offset + 0, fillType.toCode)
      this
   }

   override def instrumentId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 1)
   }

   override def instrumentId(instrumentId: Int): ExecutionReportEventBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 1, instrumentId)
      this
   }

   override def price(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 5)
   }

   override def price(price: Double): ExecutionReportEventBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 5, price)
      this
   }

   override def quantity(): Double = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getDouble(this.offset + 13)
   }

   override def quantity(quantity: Double): ExecutionReportEventBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setDouble(this.offset + 13, quantity)
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
