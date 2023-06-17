// Generated at Sat Jun 17 21:18:13 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class PlaceOrderResponseArrayCodec
    extends GenericArrayCodec[PlaceOrderResponse, PlaceOrderResponseBuilder](() => new PlaceOrderResponseCodec, value => value, PlaceOrderResponseCodec.REQUIRED_SIZE)
    with PlaceOrderResponseArrayBuilder
    with Flyweight[PlaceOrderResponseArrayCodec] {

   override def clear(): PlaceOrderResponseArrayCodec = {
      super.clear()
      this
   }
}