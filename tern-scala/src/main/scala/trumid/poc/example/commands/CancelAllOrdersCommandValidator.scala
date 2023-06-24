// Generated at Sat Jun 24 16:49:17 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object CancelAllOrdersCommandValidator {

   def validate(cancelAllOrdersCommand: CancelAllOrdersCommand): ResultCode = {
      if(cancelAllOrdersCommand.accountId().isDefined && cancelAllOrdersCommand.accountId().get <= 0) {
         return ResultCode.fail("Invalid value for 'accountId'") // ValidatePositive
      }
      if(cancelAllOrdersCommand.userId() <= 0) {
         return ResultCode.fail("Invalid value for 'userId'") // ValidatePositive
      }
      ResultCode.OK
   }
}
