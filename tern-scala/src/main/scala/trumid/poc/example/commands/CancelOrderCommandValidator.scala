// Generated at Sun Jun 25 13:27:11 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CancelOrderCommandValidator {

   def validate(cancelOrderCommand: CancelOrderCommand): ResultCode = {
      if(cancelOrderCommand.accountId().isDefined && cancelOrderCommand.accountId().get <= 0) {
         return ResultCode.fail("Invalid value for 'accountId'") // ValidatePositive
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
