// Generated (StructValidator)
package trumid.poc.example.events

import trumid.poc.example.events._
import trumid.poc.cluster.ResultCode

object OrderBookUpdateEventValidator {

   def validate(orderBookUpdateEvent: OrderBookUpdateEvent): ResultCode = {
      if(orderBookUpdateEvent.instrumentId() <= 0) {
         return ResultCode.fail("Invalid value for 'instrumentId'") // ValidatePositive
      }
      ResultCode.OK
   }
}
