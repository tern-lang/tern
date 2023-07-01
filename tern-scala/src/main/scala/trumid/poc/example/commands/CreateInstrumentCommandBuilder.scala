// Generated at Sat Jul 01 13:00:12 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CreateInstrumentCommandBuilder extends CreateInstrumentCommand {
   def instrumentId(instrumentId: Int): CreateInstrumentCommandBuilder // PrimitiveGenerator
   def scale(scale: Int): CreateInstrumentCommandBuilder // PrimitiveGenerator
   def defaults(): CreateInstrumentCommandBuilder
   def clear(): CreateInstrumentCommandBuilder

}
