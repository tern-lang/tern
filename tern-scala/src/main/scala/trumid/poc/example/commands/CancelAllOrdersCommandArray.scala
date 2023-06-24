// Generated at Sat Jun 24 19:11:13 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait CancelAllOrdersCommandArray extends GenericArray[CancelAllOrdersCommand] {}

trait CancelAllOrdersCommandArrayBuilder extends GenericArrayBuilder[CancelAllOrdersCommand, CancelAllOrdersCommandBuilder] with CancelAllOrdersCommandArray {
   def clear(): CancelAllOrdersCommandArrayBuilder
}
