// Generated (StructTrait)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait PlaceOrderCommand {
   def instrumentId(): Int // PrimitiveGenerator
   def order(): OrderInfo // StructGenerator
   def time(): Long // PrimitiveGenerator
   def userId(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
