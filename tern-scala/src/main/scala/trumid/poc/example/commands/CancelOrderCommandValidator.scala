// Generated at Sat Jul 01 13:00:12 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CancelOrderCommandValidator {

   def validate(cancelOrderCommand: CancelOrderCommand): ResultCode = {
      if(cancelOrderCommand.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      if(cancelOrderCommand.orderId().length() == 0) {
         return ResultCode.fail("Invalid value for 'orderId'") // ValidateNotBlank
      }
      if(cancelOrderCommand.time() <= 0) {
         return ResultCode.fail("Invalid value for 'time'") // ValidatePositive
      }
      if(cancelOrderCommand.userId() <= 0) {
         return ResultCode.fail("Invalid value for 'userId'") // ValidatePositive
      }
      ResultCode.OK
   }
}
