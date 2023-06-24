// Generated at Sat Jun 17 19:39:26 BST 2023 (StructTrait)
package trumid.poc.example

import trumid.poc.example._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait Order {
   def description(): Option[CharSequence] // PrimitiveArrayGenerator
   def price(): Double // PrimitiveGenerator
   def quantity(): Long // PrimitiveGenerator
   def stopPrice(): Option[Double] // PrimitiveGenerator
   def symbol(): CharSequence // PrimitiveArrayGenerator
   def user(): User // StructGenerator
   def validate(): ResultCode
}
