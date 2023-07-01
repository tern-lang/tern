// Generated at Sat Jul 01 13:00:12 BST 2023 (StructArray)
package trumid.poc.example.events

import trumid.poc.common.array._

trait OrderBookSubscribeCommandArray extends GenericArray[OrderBookSubscribeCommand] {}

trait OrderBookSubscribeCommandArrayBuilder extends GenericArrayBuilder[OrderBookSubscribeCommand, OrderBookSubscribeCommandBuilder] with OrderBookSubscribeCommandArray {
   def reset(): OrderBookSubscribeCommandArrayBuilder
   def clear(): OrderBookSubscribeCommandArrayBuilder
}
