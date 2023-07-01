// Generated at Sat Jul 01 13:00:12 BST 2023 (StructCodec)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object OrderBookUpdateEventCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 12
   val TOTAL_SIZE: Int = 12
}

final class OrderBookUpdateEventCodec(variable: Boolean = true) extends OrderBookUpdateEventBuilder with Flyweight[OrderBookUpdateEventCodec] {
   private val bidsCodec: OrderCodec = new OrderCodec(variable) // (26 x 0) + 2
   private val offersCodec: OrderCodec = new OrderCodec(variable) // (26 x 0) + 2
   private var buffer: ByteBuffer = _
   private var offset: Int = _
   private var length: Int = _
   private var required: Int = _

   override def assign(buffer: ByteBuffer, offset: Int, length: Int): OrderBookUpdateEventCodec = {
      val required = if(variable) OrderBookUpdateEventCodec.REQUIRED_SIZE else OrderBookUpdateEventCodec.TOTAL_SIZE

      if(length < required) {
         throw new IllegalArgumentException("Length is " + length + " but must be at least " + required);
      }
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.required = required;
      this;
   }

   override def bids(): Order = {
      // StructGenerator
      this.buffer.setCount(this.offset + this.required);
      this.bidsCodec.assign(this.buffer, this.offset + 0, this.length - 0)
   }

   override def bids(bids: (OrderBuilder) => Unit): OrderBookUpdateEventBuilder = {
      // StructGenerator
      this.buffer.setCount(this.offset + this.required);
      bids.apply(this.bidsCodec.assign(this.buffer, this.offset + 0, this.length - 0).defaults())
      this
   }

   override def offers(): Order = {
      // StructGenerator
      this.buffer.setCount(this.offset + this.required);
      this.offersCodec.assign(this.buffer, this.offset + 6, this.length - 6)
   }

   override def offers(offers: (OrderBuilder) => Unit): OrderBookUpdateEventBuilder = {
      // StructGenerator
      this.buffer.setCount(this.offset + this.required);
      offers.apply(this.offersCodec.assign(this.buffer, this.offset + 6, this.length - 6).defaults())
      this
   }

   override def defaults(): OrderBookUpdateEventCodec = {
      bidsCodec.defaults()
      offersCodec.defaults()
      this
   }

   override def clear(): OrderBookUpdateEventCodec = {
      bidsCodec.clear()
      offersCodec.clear()
      this
   }

   override def reset(): OrderBookUpdateEventCodec = {
      bidsCodec.reset()
      offersCodec.reset()
      this
   }

   override def validate(): ResultCode = {
      OrderBookUpdateEventValidator.validate(this)
   }
}
