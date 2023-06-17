// Generated at Sat Jun 17 19:39:26 BST 2023 (StructBuilder)
package trumid.poc.example

import trumid.poc.common.array._
import trumid.poc.cluster.OptionBuilder

trait UserBuilder extends User {
   def accountId(accountId: Int): UserBuilder // PrimitiveGenerator
   def userId(userId: Int): UserBuilder // PrimitiveGenerator
   def defaults(): UserBuilder
   def clear(): UserBuilder

}
