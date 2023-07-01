// Generated at Sat Jul 01 13:00:12 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait CreateInstrumentCommandArray extends GenericArray[CreateInstrumentCommand] {}

trait CreateInstrumentCommandArrayBuilder extends GenericArrayBuilder[CreateInstrumentCommand, CreateInstrumentCommandBuilder] with CreateInstrumentCommandArray {
   def reset(): CreateInstrumentCommandArrayBuilder
   def clear(): CreateInstrumentCommandArrayBuilder
}
