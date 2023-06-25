// Generated at Sun Jun 25 12:15:27 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CancelAllOrdersResponseValidator {

   def validate(cancelAllOrdersResponse: CancelAllOrdersResponse): ResultCode = {
      if(cancelAllOrdersResponse.reason().isDefined && cancelAllOrdersResponse.reason().get.length() == 0) {
         return ResultCode.fail("Invalid value for 'reason'") // ValidateNotBlank
      }
      if(cancelAllOrdersResponse.time() <= 0) {
         return ResultCode.fail("Invalid value for 'time'") // ValidatePositive
      }
      ResultCode.OK
   }
}
