// Generated at Sat Jul 01 15:12:09 BST 2023 (StructArray)
package trumid.poc.example.events

import trumid.poc.common.array._

trait ExecutionReportEventArray extends GenericArray[ExecutionReportEvent] {}

trait ExecutionReportEventArrayBuilder extends GenericArrayBuilder[ExecutionReportEvent, ExecutionReportEventBuilder] with ExecutionReportEventArray {
   def reset(): ExecutionReportEventArrayBuilder
   def clear(): ExecutionReportEventArrayBuilder
}
