// Generated (StructArrayCodec)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class OrderBookUpdateEventArrayCodec
    extends GenericArrayCodec[OrderBookUpdateEvent, OrderBookUpdateEventBuilder](() => new OrderBookUpdateEventCodec, value => value, OrderBookUpdateEventCodec.REQUIRED_SIZE)
    with OrderBookUpdateEventArrayBuilder
    with Flyweight[OrderBookUpdateEventArrayCodec] {

   override def reset(): OrderBookUpdateEventArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): OrderBookUpdateEventArrayCodec = {
      chain.clear()
      this
   }
}