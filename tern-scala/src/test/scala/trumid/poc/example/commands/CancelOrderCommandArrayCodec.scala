// Generated at Sat Jun 17 21:18:13 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class CancelOrderCommandArrayCodec
    extends GenericArrayCodec[CancelOrderCommand, CancelOrderCommandBuilder](() => new CancelOrderCommandCodec, value => value, CancelOrderCommandCodec.REQUIRED_SIZE)
    with CancelOrderCommandArrayBuilder
    with Flyweight[CancelOrderCommandArrayCodec] {

   override def clear(): CancelOrderCommandArrayCodec = {
      super.clear()
      this
   }
}