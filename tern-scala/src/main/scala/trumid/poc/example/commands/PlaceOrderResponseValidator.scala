// Generated at Sun Jun 25 13:27:11 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object PlaceOrderResponseValidator {

   def validate(placeOrderResponse: PlaceOrderResponse): ResultCode = {
      if(placeOrderResponse.reason().isDefined && placeOrderResponse.reason().get.length() == 0) {
         return ResultCode.fail("Invalid value for 'reason'") // ValidateNotBlank
      }
      if(placeOrderResponse.time() <= 0) {
         return ResultCode.fail("Invalid value for 'time'") // ValidatePositive
      }
      ResultCode.OK
   }
}
