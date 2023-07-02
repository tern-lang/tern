package trumid.poc.impl.server.demo

import org.agrona.collections.Long2ObjectHashMap
import trumid.poc.common.message._
import trumid.poc.example.TradingEngineResponsePublisher
import trumid.poc.impl.server.demo.book._

import java.util.ArrayDeque

class TradingServiceEventOutput(header: MessageHeader, publisher: Publisher) extends OrderChannel {
  private val client = new TradingEngineResponsePublisher(publisher.consume())
  private val buys = new Long2ObjectHashMap[OrderChange]()
  private val sells = new Long2ObjectHashMap[OrderChange]()
  private val fills = new ArrayDeque[Fill]()

  def onFill(fill: Fill): Unit = {
    if(!fill.isAggressive()) {
      val order = fill.order()
      val change = OrderChange(order, -fill.quantity())

      if (order.side().isBuy()) {
        buys.put(order.orderId(), change)
      } else {
        sells.put(order.orderId(), change)
      }
    }
    fills.offer(fill)
  }

  def onPassive(order: Order): Unit = {
    val change = OrderChange(order, order.remainingQuantity())

    if (order.side().isBuy()) {
      buys.put(order.orderId(), change)
    } else {
      sells.put(order.orderId(), change)
    }
  }

  def onCancel(order: Order): Unit = {
    val change = OrderChange(order, -order.remainingQuantity())

    if (order.side().isBuy()) {
      buys.put(order.orderId(), change)
    } else {
      sells.put(order.orderId(), change)
    }
  }

  def onComplete(instrumentId: Int): Unit = {
    if (!buys.isEmpty() || !sells.isEmpty()) {
      client.subscribeOrderBookResponse(header,
        _.instrumentId(instrumentId)
          .bids(array => {
            buys.values().forEach(order => {
              array.add()
                .orderId(order.orderId())
                .price(order.price().toDouble())
                .quantity(order.quantity())
                .changeQuantity(order.changeQuantity())

            })
          })
          .offers(array => {
            sells.values().forEach(order => {
              array.add()
                .orderId(order.orderId())
                .price(order.price().toDouble())
                .quantity(order.quantity())
                .changeQuantity(order.changeQuantity())
            })
          })
        )
    }
    if(!fills.isEmpty()) {
      fills.forEach(fill => {
        client.subscribeExecutionReportResponse(header,
          _.instrumentId(instrumentId)
            .price(fill.price().toDouble())
            .quantity(fill.quantity())
            .fillType(fill.fillType())
        )
      })
    }
    fills.clear()
    buys.clear()
    sells.clear()
  }
}
