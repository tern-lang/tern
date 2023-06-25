// Generated at Sun Jun 25 17:46:15 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class OrderInfoArrayCodec
    extends GenericArrayCodec[OrderInfo, OrderInfoBuilder](() => new OrderInfoCodec, value => value, OrderInfoCodec.REQUIRED_SIZE)
    with OrderInfoArrayBuilder
    with Flyweight[OrderInfoArrayCodec] {

   override def reset(): OrderInfoArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): OrderInfoArrayCodec = {
      chain.clear()
      this
   }
}