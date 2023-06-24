// Generated at Sat Jun 17 19:39:26 BST 2023 (StructTrait)
package trumid.poc.example

import trumid.poc.common.array._
import trumid.poc.cluster.ResultCode

trait User {
   def accountId(): Int // PrimitiveGenerator
   def userId(): Int // PrimitiveGenerator
   def validate(): ResultCode
}
