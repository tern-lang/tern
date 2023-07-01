// Generated (StructTrait)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait OrderBookSubscribeCommand {
   def instrumentId(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
