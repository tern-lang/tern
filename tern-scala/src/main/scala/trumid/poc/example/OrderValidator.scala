// Generated at Sat Jun 17 19:39:26 BST 2023 (StructValidator)
package trumid.poc.example

import trumid.poc.example._
import trumid.poc.cluster.ResultCode

object OrderValidator {

   def validate(order: Order): ResultCode = {
      ResultCode.success("Ok")
   }
}
