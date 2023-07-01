// Generated at Sat Jul 01 13:00:12 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class PlaceOrderCommandArrayCodec
    extends GenericArrayCodec[PlaceOrderCommand, PlaceOrderCommandBuilder](() => new PlaceOrderCommandCodec, value => value, PlaceOrderCommandCodec.REQUIRED_SIZE)
    with PlaceOrderCommandArrayBuilder
    with Flyweight[PlaceOrderCommandArrayCodec] {

   override def reset(): PlaceOrderCommandArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): PlaceOrderCommandArrayCodec = {
      chain.clear()
      this
   }
}