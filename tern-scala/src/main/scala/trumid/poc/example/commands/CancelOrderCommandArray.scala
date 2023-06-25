// Generated at Sun Jun 25 12:15:27 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait CancelOrderCommandArray extends GenericArray[CancelOrderCommand] {}

trait CancelOrderCommandArrayBuilder extends GenericArrayBuilder[CancelOrderCommand, CancelOrderCommandBuilder] with CancelOrderCommandArray {
   def clear(): CancelOrderCommandArrayBuilder
}
