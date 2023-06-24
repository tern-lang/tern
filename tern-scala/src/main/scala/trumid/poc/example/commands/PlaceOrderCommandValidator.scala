// Generated at Sat Jun 24 16:49:17 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.cluster.ResultCode

object PlaceOrderCommandValidator {

   def validate(placeOrderCommand: PlaceOrderCommand): ResultCode = {
      if(placeOrderCommand.accountId().isDefined && placeOrderCommand.accountId().get <= 0) {
         return ResultCode.fail("Invalid value for 'accountId'") // ValidatePositive
      }
      if(placeOrderCommand.userId() <= 0) {
         return ResultCode.fail("Invalid value for 'userId'") // ValidatePositive
      }
      if(!placeOrderCommand.order().validate().success()) {
         return placeOrderCommand.order().validate()
      }
      ResultCode.OK
   }
}
