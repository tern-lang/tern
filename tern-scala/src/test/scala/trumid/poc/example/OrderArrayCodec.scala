// Generated at Sat Jun 17 19:09:50 BST 2023 (StructArrayCodec)
package trumid.poc.example

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class OrderArrayCodec
    extends GenericArrayCodec[Order, OrderBuilder](() => new OrderCodec, value => value, OrderCodec.REQUIRED_SIZE)
    with OrderArrayBuilder
    with Flyweight[OrderArrayCodec] {

   override def clear(): OrderArrayCodec = {
      super.clear()
      this
   }
}