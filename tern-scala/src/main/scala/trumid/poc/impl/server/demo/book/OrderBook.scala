package trumid.poc.impl.server.demo.book

import java.util.Iterator

trait OrderBookImage {
  def saveOrder(order: Order): Unit
  def loadOrders(consumer: (Order) => Unit)
}

class OrderBook(instrument: Instrument, channel: OrderChannel) {
  private val buyChain = new OrderChain(channel, Buy, instrument.scale)
  private val sellChain = new OrderChain(channel, Sell, instrument.scale)

  def takeSnapshot(image: OrderBookImage): Unit = {
    buyChain.activeOrders.forEachRemaining(image.saveOrder)
    sellChain.activeOrders.forEachRemaining(image.saveOrder)
  }

  def restoreSnapshot(image: OrderBookImage): Unit = {
    image.loadOrders(this.appendOrder)
  }

  def placeOrder(order: Order): FillType = {
    val fillType = if (order.side().isBuy()) {
      sellChain.processOrder(order)
    } else {
      buyChain.processOrder(order)
    }

    if (!fillType.isFull()) {
      if (order.orderType().isLimit()) {
        appendOrder(order)
      } else {
        channel.onCancel(order)
      }
    }
    fillType
  }

  def appendOrder(order: Order): Unit = {
    if (order.orderType().isLimit()) {
      if (order.side().isBuy()) {
        buyChain.appendOrder(order)
      } else {
        sellChain.appendOrder(order)
      }
    }
  }

  def removeOrder(orderId: String): Unit = {
    buyChain.removeOrder(orderId)
    sellChain.removeOrder(orderId)
  }

  def removeAllOrders(userId: Int): Unit = {
    buyChain.removeAllOrders(userId)
    sellChain.removeAllOrders(userId)
  }

  def activeOrders(side: Side): Iterator[Order] = {
    if(side.isBuy()) {
      buyChain.activeOrders()
    } else {
      sellChain.activeOrders()
    }
  }

  def instrument(): Instrument = {
    instrument
  }

  def isEmpty: Boolean = {
    buyChain.isEmpty() && sellChain.isEmpty()
  }
}

