// Generated at Sat Jul 01 15:12:09 BST 2023 (StructValidator)
package trumid.poc.example.events

import trumid.poc.cluster.ResultCode

object OrderValidator {

   def validate(order: Order): ResultCode = {
      if(order.orderId().length() == 0) {
         return ResultCode.fail("Invalid value for 'orderId'") // ValidateNotBlank
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
