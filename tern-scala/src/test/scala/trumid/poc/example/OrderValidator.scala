// Generated at Sat Jun 17 19:31:04 BST 2023 (StructValidator)
package trumid.poc.example

import trumid.poc.cluster.ResultCode

object OrderValidator {

   def validate(order: Order): ResultCode = {
      ResultCode.success("Ok")
   }
}
