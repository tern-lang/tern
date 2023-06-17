// Generated at Sat Jun 17 21:19:29 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object OrderInfoValidator {

   def validate(orderInfo: OrderInfo): ResultCode = {
      ResultCode.success("Ok")
   }
}
