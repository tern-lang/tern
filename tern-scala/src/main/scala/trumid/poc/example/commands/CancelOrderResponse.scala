// Generated at Sat Jul 01 13:00:12 BST 2023 (StructTrait)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait CancelOrderResponse {
   def reason(): Option[CharSequence] // PrimitiveArrayGenerator
   def success(): Boolean // PrimitiveGenerator
   def time(): Long // PrimitiveGenerator
   def validate(): ResultCode
}
