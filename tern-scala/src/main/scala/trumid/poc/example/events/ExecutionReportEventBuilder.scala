// Generated (StructBuilder)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait ExecutionReportEventBuilder extends ExecutionReportEvent {
   def instrumentId(instrumentId: Int): ExecutionReportEventBuilder // PrimitiveGenerator
   def price(price: Double): ExecutionReportEventBuilder // PrimitiveGenerator
   def quantity(quantity: Double): ExecutionReportEventBuilder // PrimitiveGenerator
   def defaults(): ExecutionReportEventBuilder
   def clear(): ExecutionReportEventBuilder

}
