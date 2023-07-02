// Generated (StructValidator)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.cluster.ResultCode

object OrderValidator {

   def validate(order: Order): ResultCode = {
      if(order.orderId() <= 0) {
         return ResultCode.fail("Invalid value for 'orderId'") // ValidatePositive
      }
      if(order.price() <= 0) {
         return ResultCode.fail("Invalid value for 'price'") // ValidatePositive
      }
      if(order.quantity() <= 0) {
         return ResultCode.fail("Invalid value for 'quantity'") // ValidatePositive
      }
      ResultCode.OK
   }
}
