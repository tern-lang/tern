// Generated (StructArray)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.common.array._

trait OrderBookUpdateEventArray extends GenericArray[OrderBookUpdateEvent] {}

trait OrderBookUpdateEventArrayBuilder extends GenericArrayBuilder[OrderBookUpdateEvent, OrderBookUpdateEventBuilder] with OrderBookUpdateEventArray {
   def reset(): OrderBookUpdateEventArrayBuilder
   def clear(): OrderBookUpdateEventArrayBuilder
}
