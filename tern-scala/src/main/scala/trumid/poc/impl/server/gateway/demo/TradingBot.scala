package trumid.poc.impl.server.gateway.demo

import trumid.poc.common.message._
import trumid.poc.example.TradingEngineClient
import trumid.poc.example.commands._
import trumid.poc.example.events._

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

class TradingBot(publisher: TradingEngineClient) {

  class OrderBookConsumer extends StreamConsumer[OrderBookUpdateEvent] {

    override def onUpdate(value: OrderBookUpdateEvent) = {
      println(s"ORDER BOOK UPDATE ${value.instrumentId()}")
      value.bids().iterator().foreach(order => {
        println(order.orderId().length())
        try {
          println(order.orderId().toString())
        } catch  {
          case e: Throwable => {
            e.printStackTrace()
          }
        }
        println(s"   BID (${order.orderId()} ${order.quantity()}@${order.price()}")
      })
      value.offers().iterator().foreach(order => {
        println(order.orderId().length())
        try {
          println(order.orderId().toString())
        } catch  {
          case e: Throwable => {
            e.printStackTrace()
          }
        }
        println(s"   OFFER (${order.orderId()} ${order.quantity()}@${order.price()}")
      })
    }

    override def onFlush() = {
      //println(s"onFlush()")

    }

    override def onClose() = {

    }
  }

  class ExecutionReportConsumer extends StreamConsumer[ExecutionReportEvent] {

    override def onUpdate(value: ExecutionReportEvent) = {
      println(s"EXECUTION REPORT UPDATE ${value.instrumentId()}")
    }

    override def onFlush() = {
      //println(s"onFlush()")

    }

    override def onClose() = {

    }
  }


  def execute(count: Int) = {
    Await.result(publisher.createInstrument(_.instrumentId(1).scale(2))
      .call(_ => {
        println("Successfully created instrument")
      }), FiniteDuration(10, TimeUnit.SECONDS))

    publisher.subscribeOrderBook(_.instrumentId(1)).start(new OrderBookConsumer())
    publisher.subscribeExecutionReport(_.instrumentId(1)).start(new ExecutionReportConsumer())
    val random = new java.util.Random()
    for (i: Int <- 1 to count) {
      val call = publisher.placeOrder(
        _.userId(i)
          .instrumentId(1)
          .time(System.currentTimeMillis())
          .order(
            _.price(11.0)
              .quantity(5555)
              .orderId(s"order${i}")
              .side(if(random.nextBoolean()) Side.BUY else Side.SELL)
              .orderType(OrderType.LIMIT)))

      if (i % 10000 == 0) {
        Thread.`yield`()
      }
      handle(call)
    }
  }

  private def handle(call: Call[PlaceOrderResponse]) = {
    call.call(response => response.time()).onComplete {
      case Success(time) => {
        println(s"response OK")
      }
      case Failure(cause) => {
        cause.printStackTrace()
      }
    }(ExecutionContext.global)
  }
}
