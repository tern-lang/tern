// Generated (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBuilder extends Order {
   def orderId(orderId: Long): OrderBuilder // PrimitiveGenerator
   def orderType(orderType: OrderType): OrderBuilder // EnumGenerator
   def price(price: Double): OrderBuilder // PrimitiveGenerator
   def quantity(quantity: Long): OrderBuilder // PrimitiveGenerator
   def side(side: Side): OrderBuilder // EnumGenerator
   def defaults(): OrderBuilder
   def clear(): OrderBuilder

}
