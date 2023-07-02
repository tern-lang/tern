// Generated (EnumTrait)
package trumid.poc.example.events

import trumid.poc.example.events._

object FillType {

   object FULL extends FillType {
      override def isFull(): Boolean = true
      override def toCode(): Byte = 0
      override def toString(): String = "FULL"
   }

   object PARTIAL extends FillType {
      override def isPartial(): Boolean = true
      override def toCode(): Byte = 1
      override def toString(): String = "PARTIAL"
   }

   def resolve(code: Byte): FillType = code match {
      case 0 => FillType.FULL
      case 1 => FillType.PARTIAL
      case _ => throw new IllegalArgumentException("FillType code not matched for " + code)
   }
}

sealed trait FillType {
   def isFull(): Boolean = false
   def isPartial(): Boolean = false
   def toCode(): Byte
}
