// Generated (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CancelOrderCommandBuilder extends CancelOrderCommand {
   def instrumentId(instrumentId: Int): CancelOrderCommandBuilder // PrimitiveGenerator
   def orderId(orderId: Long): CancelOrderCommandBuilder // PrimitiveGenerator
   def time(time: Long): CancelOrderCommandBuilder // PrimitiveGenerator
   def userId(userId: Int): CancelOrderCommandBuilder // PrimitiveGenerator
   def defaults(): CancelOrderCommandBuilder
   def clear(): CancelOrderCommandBuilder

}
