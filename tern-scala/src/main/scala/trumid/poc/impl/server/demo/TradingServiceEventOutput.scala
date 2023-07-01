package trumid.poc.impl.server.demo

import org.agrona.collections.Object2ObjectHashMap
import trumid.poc.common.message._
import trumid.poc.example.TradingEngineResponsePublisher
import trumid.poc.impl.server.demo.book._

import java.util.ArrayDeque

class TradingServiceEventOutput(header: MessageHeader, publisher: Publisher) extends OrderChannel {
  private val client = new TradingEngineResponsePublisher(publisher.consume())
  private val buys = new Object2ObjectHashMap[String, Order]()
  private val sells = new Object2ObjectHashMap[String, Order]()
  private val fills = new ArrayDeque[Fill]()

  def onFill(fill: Fill): Unit = {
    fills.offer(fill)
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
      buys.put(order.orderId(), order.cancel())
    } else {
      sells.put(order.orderId(), order.cancel())
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
                .changeQuantity(if(order.active()) {
                  order.remainingQuantity()
                } else {
                  -order.remainingQuantity()
                })

            })
          })
          .offers(array => {
            sells.values().forEach(order => {
              array.add()
                .orderId(order.orderId())
                .price(order.price().toDouble())
                .quantity(order.quantity())
                .changeQuantity(if(order.active()) {
                  order.remainingQuantity()
                } else {
                  -order.remainingQuantity()
                })
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
        )
      })
    }
    fills.clear()
    buys.clear()
    sells.clear()
  }
}
