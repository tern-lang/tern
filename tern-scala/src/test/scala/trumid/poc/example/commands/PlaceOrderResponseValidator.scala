// Generated at Sat Jun 24 14:37:07 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.cluster.ResultCode

object PlaceOrderResponseValidator {

   def validate(placeOrderResponse: PlaceOrderResponse): ResultCode = {
      ResultCode.success("Ok")
   }
}
