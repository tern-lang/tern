// Generated (StructValidator)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.cluster.ResultCode

object ExecutionReportEventValidator {

   def validate(executionReportEvent: ExecutionReportEvent): ResultCode = {
      if(executionReportEvent.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      if(executionReportEvent.price() <= 0) {
         return ResultCode.fail("Invalid value for 'price'") // ValidatePositive
      }
      if(executionReportEvent.quantity() <= 0) {
         return ResultCode.fail("Invalid value for 'quantity'") // ValidatePositive
      }
      ResultCode.OK
   }
}
