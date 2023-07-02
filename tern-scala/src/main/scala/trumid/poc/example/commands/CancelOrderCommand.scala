// Generated (StructTrait)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait CancelOrderCommand {
   def instrumentId(): Int // PrimitiveGenerator
   def orderId(): Long // PrimitiveGenerator
   def time(): Long // PrimitiveGenerator
   def userId(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
