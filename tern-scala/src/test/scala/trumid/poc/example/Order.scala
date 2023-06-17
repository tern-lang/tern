// Generated at Sat Jun 17 19:31:04 BST 2023 (StructTrait)
package trumid.poc.example

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait Order {
   def description(): Option[CharSequence] // PrimitiveArrayGenerator
   def price(): Double // PrimitiveGenerator
   def quantity(): Long // PrimitiveGenerator
   def symbol(): CharSequence // PrimitiveArrayGenerator
   def validate(): ResultCode
}
