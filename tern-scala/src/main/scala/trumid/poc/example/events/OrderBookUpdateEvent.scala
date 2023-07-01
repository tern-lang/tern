// Generated at Sat Jul 01 15:12:09 BST 2023 (StructTrait)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait OrderBookUpdateEvent {
   def bids(): OrderArray // StructArrayGenerator
   def instrumentId(): Int // PrimitiveGenerator
   def offers(): OrderArray // StructArrayGenerator
   def validate(): ResultCode
}
