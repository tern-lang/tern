// Generated at Sat Jun 17 21:19:29 BST 2023 (StructValidator)
package trumid.poc.example.commands

import trumid.poc.example.commands._
import trumid.poc.cluster.ResultCode

object PlaceOrderCommandValidator {

   def validate(placeOrderCommand: PlaceOrderCommand): ResultCode = {
      ResultCode.success("Ok")
   }
}
