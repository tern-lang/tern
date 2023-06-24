// Generated at Sat Jun 24 19:11:13 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CancelOrderCommandBuilder extends CancelOrderCommand {
   def accountId(accountId: Option[Int]): CancelOrderCommandBuilder // PrimitiveGenerator
   def orderId(orderId: CharSequence): CancelOrderCommandBuilder // PrimitiveArrayGenerator
   def time(time: Long): CancelOrderCommandBuilder // PrimitiveGenerator
   def userId(userId: Int): CancelOrderCommandBuilder // PrimitiveGenerator
   def defaults(): CancelOrderCommandBuilder
   def clear(): CancelOrderCommandBuilder

}