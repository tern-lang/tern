// Generated at Sat Jun 17 21:18:13 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class PlaceOrderCommandArrayCodec
    extends GenericArrayCodec[PlaceOrderCommand, PlaceOrderCommandBuilder](() => new PlaceOrderCommandCodec, value => value, PlaceOrderCommandCodec.REQUIRED_SIZE)
    with PlaceOrderCommandArrayBuilder
    with Flyweight[PlaceOrderCommandArrayCodec] {

   override def clear(): PlaceOrderCommandArrayCodec = {
      super.clear()
      this
   }
}