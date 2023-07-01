// Generated at Sat Jul 01 13:00:12 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CreateInstrumentResponseBuilder extends CreateInstrumentResponse {
   def reason(reason: Option[CharSequence]): CreateInstrumentResponseBuilder // PrimitiveArrayGenerator
   def success(success: Boolean): CreateInstrumentResponseBuilder // PrimitiveGenerator
   def defaults(): CreateInstrumentResponseBuilder
   def clear(): CreateInstrumentResponseBuilder

}
