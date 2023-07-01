// Generated (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class CancelAllOrdersResponseArrayCodec
    extends GenericArrayCodec[CancelAllOrdersResponse, CancelAllOrdersResponseBuilder](() => new CancelAllOrdersResponseCodec, value => value, CancelAllOrdersResponseCodec.REQUIRED_SIZE)
    with CancelAllOrdersResponseArrayBuilder
    with Flyweight[CancelAllOrdersResponseArrayCodec] {

   override def reset(): CancelAllOrdersResponseArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): CancelAllOrdersResponseArrayCodec = {
      chain.clear()
      this
   }
}