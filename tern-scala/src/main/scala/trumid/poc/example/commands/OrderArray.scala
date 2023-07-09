// Generated (StructArray)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._

trait OrderArray extends GenericArray[Order] {}

trait OrderArrayBuilder extends GenericArrayBuilder[Order, OrderBuilder] with OrderArray {
   def reset(): OrderArrayBuilder
   def clear(): OrderArrayBuilder
}