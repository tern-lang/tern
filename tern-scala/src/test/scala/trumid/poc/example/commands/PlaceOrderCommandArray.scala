// Generated at Sat Jun 17 21:18:13 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._

trait PlaceOrderCommandArray extends GenericArray[PlaceOrderCommand] {}

trait PlaceOrderCommandArrayBuilder extends GenericArrayBuilder[PlaceOrderCommand, PlaceOrderCommandBuilder] with PlaceOrderCommandArray {
   def clear(): PlaceOrderCommandArrayBuilder
}
