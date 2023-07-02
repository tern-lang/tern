// Generated (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CancelOrderCommandValidator {

   def validate(cancelOrderCommand: CancelOrderCommand): ResultCode = {
      if(cancelOrderCommand.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      if(cancelOrderCommand.orderId() <= 0) {
         return ResultCode.fail("Invalid value for 'orderId'") // ValidatePositive
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
