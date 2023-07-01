// Generated at Sat Jul 01 15:12:09 BST 2023 (StructBuilder)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBookUpdateEventBuilder extends OrderBookUpdateEvent {
   def bids(bids: (OrderArrayBuilder) => Unit): OrderBookUpdateEventBuilder // StructArrayGenerator
   def instrumentId(instrumentId: Int): OrderBookUpdateEventBuilder // PrimitiveGenerator
   def offers(offers: (OrderArrayBuilder) => Unit): OrderBookUpdateEventBuilder // StructArrayGenerator
   def defaults(): OrderBookUpdateEventBuilder
   def clear(): OrderBookUpdateEventBuilder

}
