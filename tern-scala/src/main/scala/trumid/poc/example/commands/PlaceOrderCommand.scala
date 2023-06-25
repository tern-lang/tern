// Generated at Sun Jun 25 13:27:11 BST 2023 (StructTrait)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait PlaceOrderCommand {
   def accountId(): Option[Int] // PrimitiveGenerator
   def order(): OrderInfo // StructGenerator
   def time(): Long // PrimitiveGenerator
   def userId(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
