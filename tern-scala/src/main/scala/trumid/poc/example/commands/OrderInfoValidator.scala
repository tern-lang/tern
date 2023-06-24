// Generated at Sat Jun 24 19:11:13 BST 2023 (StructValidator)
package trumid.poc.example.commands

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
      if(orderInfo.symbol().length() == 0) {
         return ResultCode.fail("Invalid value for 'symbol'") // ValidateNotBlank
      }
      ResultCode.OK
   }
}
