// Generated at Sat Jul 01 13:00:12 BST 2023 (StructBuilder)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBuilder extends Order {
   def orderId(orderId: CharSequence): OrderBuilder // PrimitiveArrayGenerator
   def price(price: Double): OrderBuilder // PrimitiveGenerator
   def quantity(quantity: Double): OrderBuilder // PrimitiveGenerator
   def defaults(): OrderBuilder
   def clear(): OrderBuilder

}
