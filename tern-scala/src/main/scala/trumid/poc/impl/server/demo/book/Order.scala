package trumid.poc.impl.server.demo.book

import java.lang.{Long => LongMath}

object PriceScale {
  val DEFAULT = PriceScale(2)

  def apply(scale: Int) = {
    new PriceScale(Math.pow(10, scale).longValue())
  }
}

class PriceScale(exponent: Long) {

  def toDouble(price: Price): Double = {
    price.value.doubleValue() / exponent
  }

  def toPrice(value: Long): Price = {
    Price((value * exponent).longValue(), this)
  }

  def toPrice(value: Double): Price = {
    Price((value * exponent).longValue(), this)
  }
}

object Price {

  def apply(value: Int): Price = {
    PriceScale.DEFAULT.toPrice(value)
  }

  def apply(value: Long): Price = {
    PriceScale.DEFAULT.toPrice(value)
  }

  def apply(value: Float): Price = {
    PriceScale.DEFAULT.toPrice(value)
  }

  def apply(value: Double): Price = {
    PriceScale.DEFAULT.toPrice(value)
  }
}

case class Price(value: Long, scale: PriceScale) {

  def toDouble(): Double = {
    scale.toDouble(this)
  }

  override def toString(): String = {
    toDouble().toString()
  }
}

class Order(userId: Int, orderId: String, side: Side, orderType: OrderType, price: Price, quantity: Long) {
  private var filledQuantity: Long = 0

  def userId(): Int = userId

  def orderId(): String = orderId

  def price(): Price = price

  def quantity(): Long = quantity

  def remainingQuantity(): Long = quantity - filledQuantity

  def side(): Side = side

  def orderType(): OrderType = orderType

  def fullOrder(agressiveOrder: Order): Fill = {
    val availableQuantity = Math.min(agressiveOrder.remainingQuantity, remainingQuantity)

    filledQuantity += availableQuantity
    agressiveOrder.filledQuantity += availableQuantity

    new Fill(
      if (remainingQuantity <= 0) Full else Partial,
      side,
      price,
      availableQuantity)
  }
}

sealed trait Side {
  def isBuy(): Boolean = false

  def isSell(): Boolean = false

  def compare(left: Order, right: Order): Int
}

object Buy extends Side {
  override def isBuy(): Boolean = true

  override def compare(left: Order, right: Order) = LongMath.compare(right.price.value, left.price.value)

  override def toString(): String = "BUY"
}

object Sell extends Side {
  override def isSell(): Boolean = true

  override def compare(left: Order, right: Order) = LongMath.compare(left.price.value, right.price.value)

  override def toString(): String = "SELL"
}

sealed trait OrderType {
  def isLimit(): Boolean = false

  def isMarket(): Boolean = false
}

object Limit extends OrderType {
  override def isLimit(): Boolean = true

  override def toString(): String = "LIMIT"
}

object Market extends OrderType {
  override def isMarket(): Boolean = true

  override def toString(): String = "MARKET"
}

sealed trait FillType {
  def isFull(): Boolean = false

  def isPartial(): Boolean = false
}

object Full extends FillType {
  override def isFull(): Boolean = true

  override def toString(): String = "FULL"
}

object Partial extends FillType {
  override def isPartial(): Boolean = true

  override def toString(): String = "PARTIAL"
}

class Fill(fillType: FillType, side: Side, price: Price, quantity: Long) {
  def price(): Price = price

  def quantity(): Long = quantity

  def side(): Side = side

  def fillType(): FillType = fillType
}

trait OrderChannel {
  def onFill(fill: Fill): Unit

  def onPassive(order: Order): Unit

  def onCancel(order: Order): Unit
}