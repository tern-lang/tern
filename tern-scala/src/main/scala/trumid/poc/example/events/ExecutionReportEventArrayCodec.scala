// Generated at Sat Jul 01 15:12:09 BST 2023 (StructArrayCodec)
package trumid.poc.example.events

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class ExecutionReportEventArrayCodec
    extends GenericArrayCodec[ExecutionReportEvent, ExecutionReportEventBuilder](() => new ExecutionReportEventCodec, value => value, ExecutionReportEventCodec.REQUIRED_SIZE)
    with ExecutionReportEventArrayBuilder
    with Flyweight[ExecutionReportEventArrayCodec] {

   override def reset(): ExecutionReportEventArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): ExecutionReportEventArrayCodec = {
      chain.clear()
      this
   }
}