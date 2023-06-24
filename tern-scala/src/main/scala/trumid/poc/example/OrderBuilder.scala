// Generated at Sat Jun 17 19:39:26 BST 2023 (StructBuilder)
package trumid.poc.example

import trumid.poc.example._
import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBuilder extends Order {
   def description(description: Option[CharSequence]): OrderBuilder // PrimitiveArrayGenerator
   def price(price: Double): OrderBuilder // PrimitiveGenerator
   def quantity(quantity: Long): OrderBuilder // PrimitiveGenerator
   def stopPrice(stopPrice: Option[Double]): OrderBuilder // PrimitiveGenerator
   def symbol(symbol: CharSequence): OrderBuilder // PrimitiveArrayGenerator
   def user(user: (UserBuilder) => Unit): OrderBuilder // StructGenerator
   def defaults(): OrderBuilder
   def clear(): OrderBuilder

}
