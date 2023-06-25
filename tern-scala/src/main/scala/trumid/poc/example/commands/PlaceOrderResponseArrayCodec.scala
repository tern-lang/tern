// Generated at Sun Jun 25 17:46:15 BST 2023 (StructArrayCodec)
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