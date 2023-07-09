// Generated (StructArrayCodec)
package trumid.poc.example.commands

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class CreateInstrumentResponseArrayCodec
    extends GenericArrayCodec[CreateInstrumentResponse, CreateInstrumentResponseBuilder](() => new CreateInstrumentResponseCodec, value => value, CreateInstrumentResponseCodec.REQUIRED_SIZE)
    with CreateInstrumentResponseArrayBuilder
    with Flyweight[CreateInstrumentResponseArrayCodec] {

   override def reset(): CreateInstrumentResponseArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): CreateInstrumentResponseArrayCodec = {
      chain.clear()
      this
   }
}