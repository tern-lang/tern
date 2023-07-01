// Generated (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait CancelOrderCommandArray extends GenericArray[CancelOrderCommand] {}

trait CancelOrderCommandArrayBuilder extends GenericArrayBuilder[CancelOrderCommand, CancelOrderCommandBuilder] with CancelOrderCommandArray {
   def reset(): CancelOrderCommandArrayBuilder
   def clear(): CancelOrderCommandArrayBuilder
}
