// Generated at Sat Jul 01 13:00:12 BST 2023 (StructBuilder)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBookSubscribeCommandBuilder extends OrderBookSubscribeCommand {
   def instrumentId(instrumentId: Int): OrderBookSubscribeCommandBuilder // PrimitiveGenerator
   def defaults(): OrderBookSubscribeCommandBuilder
   def clear(): OrderBookSubscribeCommandBuilder

}
