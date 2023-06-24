// Generated at Sat Jun 17 19:39:26 BST 2023 (StructValidator)
package trumid.poc.example

import trumid.poc.cluster.ResultCode

object UserValidator {

   def validate(user: User): ResultCode = {
      ResultCode.success("Ok")
   }
}
