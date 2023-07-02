package trumid.poc.impl.server.demo.book

import trumid.poc.example.commands.Side
import trumid.poc.example.events.FillType

import java.util.Iterator
import java.util.stream.Collectors

class OrderChain(channel: OrderChannel, side: Side, scale: PriceScale) {
  private val comparator = new OrderComparator(side)
  private val orders = new OrderHeap(comparator)

  def processOrder(aggressiveOrder: Order): FillType = {
    var fillPrice = 0l;
    var fillQuantity = 0l

    while (aggressiveOrder.remainingQuantity() > 0) {
      val passiveOrder = orders.peek()

      if (passiveOrder.isDefined && comparator.compare(aggressiveOrder, passiveOrder.get) >= 0) {
        val fill = passiveOrder.get.fillOrder(aggressiveOrder)

        if(fill.fillType().isFull()) {
          orders.poll()
        }
        fillPrice += fill.price().value * fill.quantity()
        fillQuantity += fill.quantity()
        channel.onFill(fill)
      } else {
        if(aggressiveOrder.quantity > aggressiveOrder.remainingQuantity) {
          val fill = new Fill(
            FillType.PARTIAL,
            aggressiveOrder,
            Price(fillPrice / fillQuantity, scale),
            fillQuantity,
            true)

          channel.onFill(fill)
        }
        return FillType.PARTIAL
      }
    }
    val fill = new Fill(
      FillType.FULL,
      aggressiveOrder,
      aggressiveOrder.price,
      aggressiveOrder.quantity,
      true)

    channel.onFill(fill)
    fill.fillType()
  }

  def appendOrder(order: Order) = {
    channel.onPassive(order)
    orders.offer(order)
  }

  def removeOrder(orderId: Long) = {
    orders.remove(orderId).map(channel.onCancel)
  }

  def removeAllOrders(userId: Int) = {
    orders.stream(userId).forEach(order => {
      orders.remove(order.orderId)
      channel.onCancel(order)
    })
  }

  def activeOrders(): Iterator[Order] = {
    orders.stream()
      .sorted(comparator)
      .collect(Collectors.toList())
      .iterator()
  }

  def isEmpty(): Boolean = {
    orders.isEmpty()
  }
}
