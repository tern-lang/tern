// Generated at Sat Jun 24 14:37:07 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderInfoBuilder extends OrderInfo {
   def orderId(orderId: CharSequence): OrderInfoBuilder // PrimitiveArrayGenerator
   def price(price: Double): OrderInfoBuilder // PrimitiveGenerator
   def quantity(quantity: Double): OrderInfoBuilder // PrimitiveGenerator
   def symbol(symbol: CharSequence): OrderInfoBuilder // PrimitiveArrayGenerator
   def defaults(): OrderInfoBuilder
   def clear(): OrderInfoBuilder

}
