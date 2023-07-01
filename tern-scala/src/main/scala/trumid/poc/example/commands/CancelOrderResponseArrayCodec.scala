// Generated at Sat Jul 01 15:12:09 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class CancelOrderResponseArrayCodec
    extends GenericArrayCodec[CancelOrderResponse, CancelOrderResponseBuilder](() => new CancelOrderResponseCodec, value => value, CancelOrderResponseCodec.REQUIRED_SIZE)
    with CancelOrderResponseArrayBuilder
    with Flyweight[CancelOrderResponseArrayCodec] {

   override def reset(): CancelOrderResponseArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): CancelOrderResponseArrayCodec = {
      chain.clear()
      this
   }
}