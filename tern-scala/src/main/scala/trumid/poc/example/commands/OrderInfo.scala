// Generated at Sat Jul 01 13:00:12 BST 2023 (StructTrait)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait OrderInfo {
   def orderId(): CharSequence // PrimitiveArrayGenerator
   def orderType(): OrderType // EnumGenerator
   def price(): Double // PrimitiveGenerator
   def quantity(): Long // PrimitiveGenerator
   def side(): Side // EnumGenerator
   def validate(): ResultCode
}
