// Generated at Sat Jun 24 16:49:17 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait PlaceOrderCommandBuilder extends PlaceOrderCommand {
   def accountId(accountId: Option[Int]): PlaceOrderCommandBuilder // PrimitiveGenerator
   def order(order: (OrderInfoBuilder) => Unit): PlaceOrderCommandBuilder // StructGenerator
   def userId(userId: Int): PlaceOrderCommandBuilder // PrimitiveGenerator
   def defaults(): PlaceOrderCommandBuilder
   def clear(): PlaceOrderCommandBuilder

}
