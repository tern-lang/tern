// Generated at Sat Jun 24 14:37:07 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CancelAllOrdersResponseBuilder extends CancelAllOrdersResponse {
   def reason(reason: Option[CharSequence]): CancelAllOrdersResponseBuilder // PrimitiveArrayGenerator
   def success(success: Boolean): CancelAllOrdersResponseBuilder // PrimitiveGenerator
   def defaults(): CancelAllOrdersResponseBuilder
   def clear(): CancelAllOrdersResponseBuilder

}
