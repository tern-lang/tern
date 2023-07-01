// Generated at Sat Jul 01 13:00:12 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.cluster.ResultCode

object PlaceOrderCommandValidator {

   def validate(placeOrderCommand: PlaceOrderCommand): ResultCode = {
      if(placeOrderCommand.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      if(placeOrderCommand.time() <= 0) {
         return ResultCode.fail("Invalid value for 'time'") // ValidatePositive
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
