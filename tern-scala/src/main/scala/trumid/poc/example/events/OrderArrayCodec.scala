// Generated at Sat Jul 01 15:12:09 BST 2023 (StructArrayCodec)
package trumid.poc.example.events

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