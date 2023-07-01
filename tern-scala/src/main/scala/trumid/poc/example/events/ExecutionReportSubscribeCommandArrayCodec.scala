// Generated (StructArrayCodec)
package trumid.poc.example.events

import trumid.poc.common._
import trumid.poc.common.array._
import trumid.poc.common.message._

final class ExecutionReportSubscribeCommandArrayCodec
    extends GenericArrayCodec[ExecutionReportSubscribeCommand, ExecutionReportSubscribeCommandBuilder](() => new ExecutionReportSubscribeCommandCodec, value => value, ExecutionReportSubscribeCommandCodec.REQUIRED_SIZE)
    with ExecutionReportSubscribeCommandArrayBuilder
    with Flyweight[ExecutionReportSubscribeCommandArrayCodec] {

   override def reset(): ExecutionReportSubscribeCommandArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): ExecutionReportSubscribeCommandArrayCodec = {
      chain.clear()
      this
   }
}