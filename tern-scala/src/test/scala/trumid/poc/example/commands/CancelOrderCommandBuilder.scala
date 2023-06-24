// Generated at Sat Jun 24 14:37:07 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CancelOrderCommandBuilder extends CancelOrderCommand {
   def accountId(accountId: Option[Int]): CancelOrderCommandBuilder // PrimitiveGenerator
   def orderId(orderId: CharSequence): CancelOrderCommandBuilder // PrimitiveArrayGenerator
   def userId(userId: Int): CancelOrderCommandBuilder // PrimitiveGenerator
   def defaults(): CancelOrderCommandBuilder
   def clear(): CancelOrderCommandBuilder

}
