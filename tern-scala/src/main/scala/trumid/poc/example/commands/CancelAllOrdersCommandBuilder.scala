// Generated at Sat Jul 01 13:00:12 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CancelAllOrdersCommandBuilder extends CancelAllOrdersCommand {
   def instrumentId(instrumentId: Int): CancelAllOrdersCommandBuilder // PrimitiveGenerator
   def time(time: Long): CancelAllOrdersCommandBuilder // PrimitiveGenerator
   def userId(userId: Int): CancelAllOrdersCommandBuilder // PrimitiveGenerator
   def defaults(): CancelAllOrdersCommandBuilder
   def clear(): CancelAllOrdersCommandBuilder

}
