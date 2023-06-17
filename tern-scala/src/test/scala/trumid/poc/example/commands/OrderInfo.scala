// Generated at Sat Jun 17 21:19:29 BST 2023 (StructTrait)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait OrderInfo {
   def orderId(): CharSequence // PrimitiveArrayGenerator
   def price(): Double // PrimitiveGenerator
   def quantity(): Double // PrimitiveGenerator
   def symbol(): CharSequence // PrimitiveArrayGenerator
   def validate(): ResultCode
}
