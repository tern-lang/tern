// Generated (StructBuilder)
package trumid.poc.example.events

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait OrderBuilder extends Order {
   def changeQuantity(changeQuantity: Long): OrderBuilder // PrimitiveGenerator
   def orderId(orderId: Long): OrderBuilder // PrimitiveGenerator
   def price(price: Double): OrderBuilder // PrimitiveGenerator
   def quantity(quantity: Long): OrderBuilder // PrimitiveGenerator
   def defaults(): OrderBuilder
   def clear(): OrderBuilder

}