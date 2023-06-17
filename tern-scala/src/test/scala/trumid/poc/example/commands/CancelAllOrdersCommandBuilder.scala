// Generated at Sat Jun 17 21:18:13 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CancelAllOrdersCommandBuilder extends CancelAllOrdersCommand {
   def accountId(accountId: Option[Int]): CancelAllOrdersCommandBuilder // PrimitiveGenerator
   def userId(userId: Int): CancelAllOrdersCommandBuilder // PrimitiveGenerator
   def defaults(): CancelAllOrdersCommandBuilder
   def clear(): CancelAllOrdersCommandBuilder

}
