// Generated at Sat Jul 01 13:00:12 BST 2023 (StructValidator)
package trumid.poc.example.events

import trumid.poc.cluster.ResultCode

object OrderBookSubscribeCommandValidator {

   def validate(orderBookSubscribeCommand: OrderBookSubscribeCommand): ResultCode = {
      if(orderBookSubscribeCommand.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      ResultCode.OK
   }
}
