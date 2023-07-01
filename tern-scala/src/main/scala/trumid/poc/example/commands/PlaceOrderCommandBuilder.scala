// Generated (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait PlaceOrderCommandBuilder extends PlaceOrderCommand {
   def instrumentId(instrumentId: Int): PlaceOrderCommandBuilder // PrimitiveGenerator
   def order(order: (OrderInfoBuilder) => Unit): PlaceOrderCommandBuilder // StructGenerator
   def time(time: Long): PlaceOrderCommandBuilder // PrimitiveGenerator
   def userId(userId: Int): PlaceOrderCommandBuilder // PrimitiveGenerator
   def defaults(): PlaceOrderCommandBuilder
   def clear(): PlaceOrderCommandBuilder

}
