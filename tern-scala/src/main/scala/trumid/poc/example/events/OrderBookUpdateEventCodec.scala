// Generated at Sat Jul 01 15:12:09 BST 2023 (StructCodec)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common._
import trumid.poc.common.message._
import trumid.poc.common.array._
import trumid.poc.cluster._

object OrderBookUpdateEventCodec {
   val VERSION: Int = 1
   val REQUIRED_SIZE: Int = 16
   val TOTAL_SIZE: Int = 16
}

final class OrderBookUpdateEventCodec(variable: Boolean = true) extends OrderBookUpdateEventBuilder with Flyweight[OrderBookUpdateEventCodec] {
   private val bidsCodec: OrderArrayCodec = new OrderArrayCodec() // (26 x 0) + 2
   private val offersCodec: OrderArrayCodec = new OrderArrayCodec() // (26 x 0) + 2
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

   override def bids(): OrderArray = {
      // StructArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.bidsCodec.assign(
         this.buffer,
         this.offset + (0 + this.buffer.getByte(this.offset + 0 + 1)),
         this.length - (0 + this.buffer.getByte(this.offset + 0 + 1))
      )
   }

   override def bids(bids: (OrderArrayBuilder) => Unit): OrderBookUpdateEventBuilder = {
      // StructArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      bids.apply(this.bidsCodec.assign(this.buffer, this.offset + 0, this.length - 0))
      this
   }

   override def instrumentId(): Int = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.getInt(this.offset + 6)
   }

   override def instrumentId(instrumentId: Int): OrderBookUpdateEventBuilder = {
      // PrimitiveGenerator
      this.buffer.setCount(this.offset + this.required);
      this.buffer.setInt(this.offset + 6, instrumentId)
      this
   }

   override def offers(): OrderArray = {
      // StructArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      this.offersCodec.assign(
         this.buffer,
         this.offset + (10 + this.buffer.getByte(this.offset + 10 + 1)),
         this.length - (10 + this.buffer.getByte(this.offset + 10 + 1))
      )
   }

   override def offers(offers: (OrderArrayBuilder) => Unit): OrderBookUpdateEventBuilder = {
      // StructArrayGenerator
      this.buffer.setCount(this.offset + this.required);
      offers.apply(this.offersCodec.assign(this.buffer, this.offset + 10, this.length - 10))
      this
   }

   override def defaults(): OrderBookUpdateEventCodec = {
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
