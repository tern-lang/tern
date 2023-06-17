// Generated at Sat Jun 17 19:09:50 BST 2023 (StructArray)
package trumid.poc.example

import trumid.poc.common.array._

trait OrderArray extends GenericArray[Order] {}

trait OrderArrayBuilder extends GenericArrayBuilder[Order, OrderBuilder] with OrderArray {
   def clear(): OrderArrayBuilder
}
