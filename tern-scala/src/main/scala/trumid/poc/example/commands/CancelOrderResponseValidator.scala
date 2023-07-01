// Generated (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CancelOrderResponseValidator {

   def validate(cancelOrderResponse: CancelOrderResponse): ResultCode = {
      if(cancelOrderResponse.reason().isDefined && cancelOrderResponse.reason().get.length() == 0) {
         return ResultCode.fail("Invalid value for 'reason'") // ValidateNotBlank
      }
      if(cancelOrderResponse.time() <= 0) {
         return ResultCode.fail("Invalid value for 'time'") // ValidatePositive
      }
      ResultCode.OK
   }
}
