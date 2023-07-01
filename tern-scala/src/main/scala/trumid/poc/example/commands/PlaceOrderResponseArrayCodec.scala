// Generated at Sat Jul 01 13:00:12 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class PlaceOrderResponseArrayCodec
    extends GenericArrayCodec[PlaceOrderResponse, PlaceOrderResponseBuilder](() => new PlaceOrderResponseCodec, value => value, PlaceOrderResponseCodec.REQUIRED_SIZE)
    with PlaceOrderResponseArrayBuilder
    with Flyweight[PlaceOrderResponseArrayCodec] {

   override def reset(): PlaceOrderResponseArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): PlaceOrderResponseArrayCodec = {
      chain.clear()
      this
   }
}