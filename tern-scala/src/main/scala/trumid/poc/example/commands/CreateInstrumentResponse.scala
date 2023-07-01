// Generated (StructTrait)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait CreateInstrumentResponse {
   def reason(): Option[CharSequence] // PrimitiveArrayGenerator
   def success(): Boolean // PrimitiveGenerator
   def validate(): ResultCode
}
