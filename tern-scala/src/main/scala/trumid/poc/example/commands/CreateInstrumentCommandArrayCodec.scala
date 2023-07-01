// Generated at Sat Jul 01 13:00:12 BST 2023 (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class CreateInstrumentCommandArrayCodec
    extends GenericArrayCodec[CreateInstrumentCommand, CreateInstrumentCommandBuilder](() => new CreateInstrumentCommandCodec, value => value, CreateInstrumentCommandCodec.REQUIRED_SIZE)
    with CreateInstrumentCommandArrayBuilder
    with Flyweight[CreateInstrumentCommandArrayCodec] {

   override def reset(): CreateInstrumentCommandArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): CreateInstrumentCommandArrayCodec = {
      chain.clear()
      this
   }
}