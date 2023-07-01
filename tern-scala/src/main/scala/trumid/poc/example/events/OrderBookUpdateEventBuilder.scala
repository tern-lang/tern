// Generated at Sat Jul 01 13:00:12 BST 2023 (StructBuilder)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBookUpdateEventBuilder extends OrderBookUpdateEvent {
   def bids(bids: (OrderBuilder) => Unit): OrderBookUpdateEventBuilder // StructGenerator
   def offers(offers: (OrderBuilder) => Unit): OrderBookUpdateEventBuilder // StructGenerator
   def defaults(): OrderBookUpdateEventBuilder
   def clear(): OrderBookUpdateEventBuilder

}
