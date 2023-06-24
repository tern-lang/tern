// Generated at Sat Jun 24 14:37:07 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CancelAllOrdersResponseValidator {

   def validate(cancelAllOrdersResponse: CancelAllOrdersResponse): ResultCode = {
      ResultCode.success("Ok")
   }
}
