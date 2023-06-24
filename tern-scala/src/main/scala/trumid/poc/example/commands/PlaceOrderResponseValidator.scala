// Generated at Sat Jun 24 16:49:17 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object PlaceOrderResponseValidator {

   def validate(placeOrderResponse: PlaceOrderResponse): ResultCode = {
      if(placeOrderResponse.reason().isDefined && placeOrderResponse.reason().get.length() == 0) {
         return ResultCode.fail("Invalid value for 'reason'") // ValidateNotBlank
      }
      ResultCode.OK
   }
}
