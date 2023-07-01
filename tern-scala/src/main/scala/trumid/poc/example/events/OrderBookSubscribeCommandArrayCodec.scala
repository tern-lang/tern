// Generated at Sat Jul 01 15:12:09 BST 2023 (StructArrayCodec)
package trumid.poc.example.events

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class OrderBookSubscribeCommandArrayCodec
    extends GenericArrayCodec[OrderBookSubscribeCommand, OrderBookSubscribeCommandBuilder](() => new OrderBookSubscribeCommandCodec, value => value, OrderBookSubscribeCommandCodec.REQUIRED_SIZE)
    with OrderBookSubscribeCommandArrayBuilder
    with Flyweight[OrderBookSubscribeCommandArrayCodec] {

   override def reset(): OrderBookSubscribeCommandArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): OrderBookSubscribeCommandArrayCodec = {
      chain.clear()
      this
   }
}