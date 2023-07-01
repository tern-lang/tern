// Generated (StructArray)
package trumid.poc.example.events

import trumid.poc.common.array._

trait OrderBookSubscribeCommandArray extends GenericArray[OrderBookSubscribeCommand] {}

trait OrderBookSubscribeCommandArrayBuilder extends GenericArrayBuilder[OrderBookSubscribeCommand, OrderBookSubscribeCommandBuilder] with OrderBookSubscribeCommandArray {
   def reset(): OrderBookSubscribeCommandArrayBuilder
   def clear(): OrderBookSubscribeCommandArrayBuilder
}
