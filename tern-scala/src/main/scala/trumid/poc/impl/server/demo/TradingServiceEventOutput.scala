package trumid.poc.impl.server.demo

import org.agrona.collections.Object2ObjectHashMap
import trumid.poc.common.message._
import trumid.poc.impl.server.demo.book._

class TradingServiceEventOutput(header: MessageHeader, publisher: Publisher) extends OrderChannel {
  private val buys = new Object2ObjectHashMap[String, Order]()
  private val sells = new Object2ObjectHashMap[String, Order]()

  def onFill(fill: Fill): Unit = {

  }

  def onPassive(order: Order): Unit = {
    if (order.side().isBuy()) {
      buys.put(order.orderId(), order)
    } else {
      sells.put(order.orderId(), order)
    }
  }

  def onCancel(order: Order): Unit = {
    if (order.side().isBuy()) {
      buys.put(order.orderId(), order)
    } else {
      sells.put(order.orderId(), order)
    }
  }

  def onComplete(): Unit = {
    buys.clear()
    sells.clear()
  }

}
