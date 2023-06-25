// Generated at Sun Jun 25 17:46:15 BST 2023 (StructBuilder)
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
