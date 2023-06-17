// Generated at Sat Jun 17 21:19:29 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait PlaceOrderResponseBuilder extends PlaceOrderResponse {
   def reason(reason: Option[CharSequence]): PlaceOrderResponseBuilder // PrimitiveArrayGenerator
   def success(success: Boolean): PlaceOrderResponseBuilder // PrimitiveGenerator
   def defaults(): PlaceOrderResponseBuilder
   def clear(): PlaceOrderResponseBuilder

}
