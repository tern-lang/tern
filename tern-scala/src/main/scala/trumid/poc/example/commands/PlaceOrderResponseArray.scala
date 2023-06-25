// Generated at Sun Jun 25 16:31:14 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait PlaceOrderResponseArray extends GenericArray[PlaceOrderResponse] {}

trait PlaceOrderResponseArrayBuilder extends GenericArrayBuilder[PlaceOrderResponse, PlaceOrderResponseBuilder] with PlaceOrderResponseArray {
   def reset(): PlaceOrderResponseArrayBuilder
   def clear(): PlaceOrderResponseArrayBuilder
}
