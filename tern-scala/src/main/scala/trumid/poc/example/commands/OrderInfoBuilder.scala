// Generated at Sat Jul 01 13:00:12 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderInfoBuilder extends OrderInfo {
   def orderId(orderId: CharSequence): OrderInfoBuilder // PrimitiveArrayGenerator
   def orderType(orderType: OrderType): OrderInfoBuilder // EnumGenerator
   def price(price: Double): OrderInfoBuilder // PrimitiveGenerator
   def quantity(quantity: Long): OrderInfoBuilder // PrimitiveGenerator
   def side(side: Side): OrderInfoBuilder // EnumGenerator
   def defaults(): OrderInfoBuilder
   def clear(): OrderInfoBuilder

}
