// Generated at Sun Jun 25 17:46:15 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait OrderInfoArray extends GenericArray[OrderInfo] {}

trait OrderInfoArrayBuilder extends GenericArrayBuilder[OrderInfo, OrderInfoBuilder] with OrderInfoArray {
   def reset(): OrderInfoArrayBuilder
   def clear(): OrderInfoArrayBuilder
}
