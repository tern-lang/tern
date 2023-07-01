// Generated (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CancelAllOrdersCommandValidator {

   def validate(cancelAllOrdersCommand: CancelAllOrdersCommand): ResultCode = {
      if(cancelAllOrdersCommand.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      if(cancelAllOrdersCommand.time() <= 0) {
         return ResultCode.fail("Invalid value for 'time'") // ValidatePositive
      }
      if(cancelAllOrdersCommand.userId() <= 0) {
         return ResultCode.fail("Invalid value for 'userId'") // ValidatePositive
      }
      ResultCode.OK
   }
}
