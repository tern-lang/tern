// Generated at Sat Jul 01 13:00:12 BST 2023 (EnumTrait)
package trumid.poc.example.commands

import trumid.poc.example.commands._

object Side {

   object BUY extends Side {
      override def isBuy(): Boolean = true
      override def toCode(): Byte = 0
      override def toString(): String = "BUY"
   }

   object SELL extends Side {
      override def isSell(): Boolean = true
      override def toCode(): Byte = 1
      override def toString(): String = "SELL"
   }

   def resolve(code: Byte): Side = code match {
      case 0 => Side.BUY
      case 1 => Side.SELL
      case _ => throw new IllegalArgumentException("Side code not matched for " + code)
   }
}

sealed trait Side {
   def isBuy(): Boolean = false
   def isSell(): Boolean = false
   def toCode(): Byte
}
