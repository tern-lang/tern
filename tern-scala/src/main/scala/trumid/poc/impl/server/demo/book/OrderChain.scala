package trumid.poc.impl.server.demo.book

import java.util.Iterator
import java.util.stream.Collectors

class OrderChain(channel: OrderChannel, side: Side, scale: PriceScale) {
  private val orders = new OrderHeap(side.compare)

  def processOrder(aggressiveOrder: Order): FillType = {
    var fillPrice = 0l;
    var fillQuantity = 0l

    while (aggressiveOrder.remainingQuantity() > 0) {
      val passiveOrder = orders.peek()

      if (passiveOrder.isDefined && side.compare(aggressiveOrder, passiveOrder.get) >= 0) {
        val fill = passiveOrder.get.fullOrder(aggressiveOrder)

        if(fill.fillType().isFull()) {
          orders.poll()
        }
        fillPrice += fill.price().value * fill.quantity()
        fillQuantity += fill.quantity()
        channel.onFill(fill)
      } else {
        if(aggressiveOrder.quantity > aggressiveOrder.remainingQuantity) {
          val fill = new Fill(
            Partial,
            aggressiveOrder.side,
            Price(fillPrice / fillQuantity, scale),
            fillQuantity)

          channel.onFill(fill)
        }
        return Partial
      }
    }
    val fill = new Fill(
      Full,
      aggressiveOrder.side,
      aggressiveOrder.price,
      aggressiveOrder.quantity)

    channel.onFill(fill)
    fill.fillType()
  }

  def appendOrder(order: Order) = {
    channel.onPassive(order)
    orders.offer(order)
  }

  def removeOrder(orderId: String) = {
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
      .sorted(side.compare)
      .collect(Collectors.toList())
      .iterator()
  }

  def isEmpty(): Boolean = {
    orders.isEmpty()
  }
}
