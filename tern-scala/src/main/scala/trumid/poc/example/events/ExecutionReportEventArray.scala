// Generated (StructArray)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common.array._

trait ExecutionReportEventArray extends GenericArray[ExecutionReportEvent] {}

trait ExecutionReportEventArrayBuilder extends GenericArrayBuilder[ExecutionReportEvent, ExecutionReportEventBuilder] with ExecutionReportEventArray {
   def reset(): ExecutionReportEventArrayBuilder
   def clear(): ExecutionReportEventArrayBuilder
}
