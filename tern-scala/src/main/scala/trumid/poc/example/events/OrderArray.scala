// Generated at Sat Jul 01 15:12:09 BST 2023 (StructArray)
package trumid.poc.example.events

import trumid.poc.common.array._

trait OrderArray extends GenericArray[Order] {}

trait OrderArrayBuilder extends GenericArrayBuilder[Order, OrderBuilder] with OrderArray {
   def reset(): OrderArrayBuilder
   def clear(): OrderArrayBuilder
}
