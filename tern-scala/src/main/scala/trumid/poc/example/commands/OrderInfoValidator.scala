// Generated (StructValidator)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.cluster.ResultCode

object OrderInfoValidator {

   def validate(orderInfo: OrderInfo): ResultCode = {
      if(orderInfo.orderId().length() == 0) {
         return ResultCode.fail("Invalid value for 'orderId'") // ValidateNotBlank
      }
      if(orderInfo.price() <= 0) {
         return ResultCode.fail("Invalid value for 'price'") // ValidatePositive
      }
      if(orderInfo.quantity() <= 0) {
         return ResultCode.fail("Invalid value for 'quantity'") // ValidatePositive
      }
      ResultCode.OK
   }
}
