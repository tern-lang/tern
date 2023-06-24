// Generated at Sat Jun 24 16:49:17 BST 2023 (StructTrait)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait CancelOrderCommand {
   def accountId(): Option[Int] // PrimitiveGenerator
   def orderId(): CharSequence // PrimitiveArrayGenerator
   def userId(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
