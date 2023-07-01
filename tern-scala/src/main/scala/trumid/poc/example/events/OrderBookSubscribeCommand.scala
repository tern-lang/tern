// Generated at Sat Jul 01 13:00:12 BST 2023 (StructTrait)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait OrderBookSubscribeCommand {
   def instrumentId(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
