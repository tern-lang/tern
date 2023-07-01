// Generated (StructTrait)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait Order {
   def changeQuantity(): Long // PrimitiveGenerator
   def orderId(): CharSequence // PrimitiveArrayGenerator
   def price(): Double // PrimitiveGenerator
   def quantity(): Long // PrimitiveGenerator
   def validate(): ResultCode
}
