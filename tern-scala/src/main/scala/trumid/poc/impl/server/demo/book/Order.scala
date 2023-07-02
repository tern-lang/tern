package trumid.poc.impl.server.demo.book

import trumid.poc.example.commands.{OrderType,Side}
import trumid.poc.example.events.FillType
import java.lang.{Long => LongMath}
import java.util.Comparator

class Order(userId: Int, orderId: Long, side: Side, orderType: OrderType, price: Price, quantity: Long) {
  private var filledQuantity: Long = 0

  def userId(): Int = userId
  def orderId(): Long = orderId
  def price(): Price = price
  def quantity(): Long = quantity
  def remainingQuantity(): Long = quantity - filledQuantity
  def side(): Side = side
  def orderType(): OrderType = orderType
  def fillOrder(agressiveOrder: Order): Fill = {
    val availableQuantity = Math.min(agressiveOrder.remainingQuantity, remainingQuantity)

    filledQuantity += availableQuantity
    agressiveOrder.filledQuantity += availableQuantity

    new Fill(
      if (remainingQuantity <= 0) FillType.FULL else FillType.PARTIAL,
      this,
      price,
      availableQuantity,
      false)
  }
}

case class OrderChange(order: Order, diffQuatity: Long) {
  def orderId(): Long = order.orderId()
  def price(): Price = order.price()
  def quantity(): Long = order.quantity()
  def changeQuantity(): Long = diffQuatity
}

class OrderComparator(side: Side) extends Comparator[Order] {

  override def compare(left: Order, right: Order) = {
    if(side.isBuy()) {
      LongMath.compare(right.price.value, left.price.value)
    } else {
      LongMath.compare(left.price.value, right.price.value)
    }
  }
}

trait OrderChannel {
  def onFill(fill: Fill): Unit
  def onPassive(order: Order): Unit
  def onCancel(order: Order): Unit
}