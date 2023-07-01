// Generated at Sat Jul 01 15:12:09 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class CancelAllOrdersCommandArrayCodec
    extends GenericArrayCodec[CancelAllOrdersCommand, CancelAllOrdersCommandBuilder](() => new CancelAllOrdersCommandCodec, value => value, CancelAllOrdersCommandCodec.REQUIRED_SIZE)
    with CancelAllOrdersCommandArrayBuilder
    with Flyweight[CancelAllOrdersCommandArrayCodec] {

   override def reset(): CancelAllOrdersCommandArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): CancelAllOrdersCommandArrayCodec = {
      chain.clear()
      this
   }
}