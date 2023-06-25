// Generated at Sun Jun 25 16:31:14 BST 2023 (StructCodec)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object PlaceOrderCommandCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 49
   val TOTAL_SIZE: Int = 49
}

final class PlaceOrderCommandCodec(variable: Boolean = true) extends PlaceOrderCommandBuilder with Flyweight[PlaceOrderCommandCodec] {
   private val orderCodec: OrderInfoCodec = new OrderInfoCodec(variable) // 28
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): PlaceOrderCommandCodec = {
      val required = if(variable) PlaceOrderCommandCodec.REQUIRED_SIZE else PlaceOrderCommandCodec.TOTAL_SIZE

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

   override def accountId(accountId: Option[Int]): PlaceOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required)
      this.buffer.setBoolean(this.offset + this.offset + 0, accountId.isDefined)
      if (accountId.isDefined) this.buffer.setInt(this.offset + this.offset + 0, accountId.get)
      this
   }

   override def order(): OrderInfo = {
      // StructGenerator
      this.buffer.setCount(this.offset + this.required);
      this.orderCodec.assign(this.buffer, this.offset + 9, this.length - 9)
   }

   override def order(order: (OrderInfoBuilder) => Unit): PlaceOrderCommandBuilder = {
      // StructGenerator
      this.buffer.setCount(this.offset + this.required);
      order.apply(this.orderCodec.assign(this.buffer, this.offset + 9, this.length - 9).defaults())
      this
   }

   override def time(): Long = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getLong(this.offset + 37)
   }

   override def time(time: Long): PlaceOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setLong(this.offset + 37, time)
      this
   }

   override def userId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 45)
   }

   override def userId(userId: Int): PlaceOrderCommandBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 45, userId)
      this
   }

   override def defaults(): PlaceOrderCommandCodec = {
      orderCodec.defaults()
      this
   }

   override def clear(): PlaceOrderCommandCodec = {
      orderCodec.clear()
      this
   }

   override def reset(): PlaceOrderCommandCodec = {
      orderCodec.reset()
      this
   }

   override def validate(): ResultCode = {
      PlaceOrderCommandValidator.validate(this)
   }
}
