// Generated at Sat Jul 01 13:00:12 BST 2023 (StructArray)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.common.array._

trait OrderInfoArray extends GenericArray[OrderInfo] {}

trait OrderInfoArrayBuilder extends GenericArrayBuilder[OrderInfo, OrderInfoBuilder] with OrderInfoArray {
   def reset(): OrderInfoArrayBuilder
   def clear(): OrderInfoArrayBuilder
}
