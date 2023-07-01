// Generated (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait CancelAllOrdersCommandArray extends GenericArray[CancelAllOrdersCommand] {}

trait CancelAllOrdersCommandArrayBuilder extends GenericArrayBuilder[CancelAllOrdersCommand, CancelAllOrdersCommandBuilder] with CancelAllOrdersCommandArray {
   def reset(): CancelAllOrdersCommandArrayBuilder
   def clear(): CancelAllOrdersCommandArrayBuilder
}
