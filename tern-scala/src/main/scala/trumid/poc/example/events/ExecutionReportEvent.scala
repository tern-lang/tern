// Generated (StructTrait)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait ExecutionReportEvent {
   def instrumentId(): Int // PrimitiveGenerator
   def price(): Double // PrimitiveGenerator
   def quantity(): Double // PrimitiveGenerator
   def validate(): ResultCode
}
