// Generated (StructBuilder)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait ExecutionReportEventBuilder extends ExecutionReportEvent {
   def fillType(fillType: FillType): ExecutionReportEventBuilder // EnumGenerator
   def instrumentId(instrumentId: Int): ExecutionReportEventBuilder // PrimitiveGenerator
   def price(price: Double): ExecutionReportEventBuilder // PrimitiveGenerator
   def quantity(quantity: Double): ExecutionReportEventBuilder // PrimitiveGenerator
   def defaults(): ExecutionReportEventBuilder
   def clear(): ExecutionReportEventBuilder

}
