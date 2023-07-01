// Generated at Sat Jul 01 15:12:09 BST 2023 (StructValidator)
package trumid.poc.example.events

import trumid.poc.cluster.ResultCode

object ExecutionReportSubscribeCommandValidator {

   def validate(executionReportSubscribeCommand: ExecutionReportSubscribeCommand): ResultCode = {
      if(executionReportSubscribeCommand.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      ResultCode.OK
   }
}
