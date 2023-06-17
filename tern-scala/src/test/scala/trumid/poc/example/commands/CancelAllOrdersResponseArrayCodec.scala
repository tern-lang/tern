// Generated at Sat Jun 17 21:18:13 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class CancelAllOrdersResponseArrayCodec
    extends GenericArrayCodec[CancelAllOrdersResponse, CancelAllOrdersResponseBuilder](() => new CancelAllOrdersResponseCodec, value => value, CancelAllOrdersResponseCodec.REQUIRED_SIZE)
    with CancelAllOrdersResponseArrayBuilder
    with Flyweight[CancelAllOrdersResponseArrayCodec] {

   override def clear(): CancelAllOrdersResponseArrayCodec = {
      super.clear()
      this
   }
}