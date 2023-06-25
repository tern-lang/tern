// Generated at Sun Jun 25 12:15:27 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CancelAllOrdersCommandBuilder extends CancelAllOrdersCommand {
   def accountId(accountId: Option[Int]): CancelAllOrdersCommandBuilder // PrimitiveGenerator
   def time(time: Long): CancelAllOrdersCommandBuilder // PrimitiveGenerator
   def userId(userId: Int): CancelAllOrdersCommandBuilder // PrimitiveGenerator
   def defaults(): CancelAllOrdersCommandBuilder
   def clear(): CancelAllOrdersCommandBuilder

}
