// Generated (StructBuilder)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBookSubscribeCommandBuilder extends OrderBookSubscribeCommand {
   def instrumentId(instrumentId: Int): OrderBookSubscribeCommandBuilder // PrimitiveGenerator
   def defaults(): OrderBookSubscribeCommandBuilder
   def clear(): OrderBookSubscribeCommandBuilder

}
