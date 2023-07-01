// Generated at Sat Jul 01 15:12:09 BST 2023 (StructTrait)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait CreateInstrumentCommand {
   def instrumentId(): Int // PrimitiveGenerator
   def scale(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
