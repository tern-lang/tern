// Generated (StructTrait)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait CancelAllOrdersCommand {
   def instrumentId(): Int // PrimitiveGenerator
   def time(): Long // PrimitiveGenerator
   def userId(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
