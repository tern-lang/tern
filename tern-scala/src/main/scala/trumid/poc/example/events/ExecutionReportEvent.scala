// Generated at Sat Jul 01 15:12:09 BST 2023 (StructTrait)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait ExecutionReportEvent {
   def instrumentId(): Int // PrimitiveGenerator
   def price(): Double // PrimitiveGenerator
   def quantity(): Double // PrimitiveGenerator
   def validate(): ResultCode
}
