// Generated (StructTrait)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait Order {
   def orderId(): CharSequence // PrimitiveArrayGenerator
   def price(): Double // PrimitiveGenerator
   def quantity(): Double // PrimitiveGenerator
   def validate(): ResultCode
}
