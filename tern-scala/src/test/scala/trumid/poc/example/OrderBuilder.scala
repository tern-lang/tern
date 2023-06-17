// Generated at Sat Jun 17 19:09:50 BST 2023 (StructBuilder)
package trumid.poc.example

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBuilder extends Order {
   def price(price: Double): OrderBuilder // PrimitiveGenerator
   def quantity(quantity: Long): OrderBuilder // PrimitiveGenerator
   def symbol(symbol: CharSequence): OrderBuilder // PrimitiveArrayGenerator
   def defaults(): OrderBuilder
   def clear(): OrderBuilder

}
