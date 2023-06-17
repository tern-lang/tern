// Generated at Sat Jun 17 21:19:29 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait CancelAllOrdersCommandArray extends GenericArray[CancelAllOrdersCommand] {}

trait CancelAllOrdersCommandArrayBuilder extends GenericArrayBuilder[CancelAllOrdersCommand, CancelAllOrdersCommandBuilder] with CancelAllOrdersCommandArray {
   def clear(): CancelAllOrdersCommandArrayBuilder
}
