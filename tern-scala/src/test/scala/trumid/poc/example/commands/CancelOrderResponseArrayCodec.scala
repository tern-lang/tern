// Generated at Sat Jun 17 21:19:29 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class CancelOrderResponseArrayCodec
    extends GenericArrayCodec[CancelOrderResponse, CancelOrderResponseBuilder](() => new CancelOrderResponseCodec, value => value, CancelOrderResponseCodec.REQUIRED_SIZE)
    with CancelOrderResponseArrayBuilder
    with Flyweight[CancelOrderResponseArrayCodec] {

   override def clear(): CancelOrderResponseArrayCodec = {
      super.clear()
      this
   }
}