// Generated at Sat Jun 17 21:18:13 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.common.array._

trait OrderInfoArray extends GenericArray[OrderInfo] {}

trait OrderInfoArrayBuilder extends GenericArrayBuilder[OrderInfo, OrderInfoBuilder] with OrderInfoArray {
   def clear(): OrderInfoArrayBuilder
}
