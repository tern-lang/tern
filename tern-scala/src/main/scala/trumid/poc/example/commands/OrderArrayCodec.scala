// Generated (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class OrderArrayCodec
    extends GenericArrayCodec[Order, OrderBuilder](() => new OrderCodec, value => value, OrderCodec.REQUIRED_SIZE)
    with OrderArrayBuilder
    with Flyweight[OrderArrayCodec] {

   override def reset(): OrderArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): OrderArrayCodec = {
      chain.clear()
      this
   }
}