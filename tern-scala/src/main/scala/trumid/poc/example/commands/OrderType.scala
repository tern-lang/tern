// Generated (EnumTrait)
package trumid.poc.example.commands

import trumid.poc.example.commands._

object OrderType {

   object LIMIT extends OrderType {
      override def isLimit(): Boolean = true
      override def toCode(): Byte = 0
      override def toString(): String = "LIMIT"
   }

   object MARKET extends OrderType {
      override def isMarket(): Boolean = true
      override def toCode(): Byte = 1
      override def toString(): String = "MARKET"
   }

   def resolve(code: Byte): OrderType = code match {
      case 0 => OrderType.LIMIT
      case 1 => OrderType.MARKET
      case _ => throw new IllegalArgumentException("OrderType code not matched for " + code)
   }
}

sealed trait OrderType {
   def isLimit(): Boolean = false
   def isMarket(): Boolean = false
   def toCode(): Byte
}
