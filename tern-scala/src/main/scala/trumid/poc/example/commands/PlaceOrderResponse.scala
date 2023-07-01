// Generated (StructTrait)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait PlaceOrderResponse {
   def reason(): Option[CharSequence] // PrimitiveArrayGenerator
   def success(): Boolean // PrimitiveGenerator
   def time(): Long // PrimitiveGenerator
   def validate(): ResultCode
}
