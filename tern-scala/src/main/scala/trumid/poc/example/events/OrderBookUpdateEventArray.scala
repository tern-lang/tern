// Generated at Sat Jul 01 13:00:12 BST 2023 (StructArray)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common.array._

trait OrderBookUpdateEventArray extends GenericArray[OrderBookUpdateEvent] {}

trait OrderBookUpdateEventArrayBuilder extends GenericArrayBuilder[OrderBookUpdateEvent, OrderBookUpdateEventBuilder] with OrderBookUpdateEventArray {
   def reset(): OrderBookUpdateEventArrayBuilder
   def clear(): OrderBookUpdateEventArrayBuilder
}
