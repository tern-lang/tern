// Generated at Sun Jun 25 17:46:15 BST 2023 (StructBuilder)
package trumid.poc.example.commands

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait CancelOrderResponseBuilder extends CancelOrderResponse {
   def reason(reason: Option[CharSequence]): CancelOrderResponseBuilder // PrimitiveArrayGenerator
   def success(success: Boolean): CancelOrderResponseBuilder // PrimitiveGenerator
   def time(time: Long): CancelOrderResponseBuilder // PrimitiveGenerator
   def defaults(): CancelOrderResponseBuilder
   def clear(): CancelOrderResponseBuilder

}
