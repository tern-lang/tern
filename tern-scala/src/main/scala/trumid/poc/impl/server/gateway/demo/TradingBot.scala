package trumid.poc.impl.server.gateway.demo

import trumid.poc.common.message._
import trumid.poc.example.TradingEngineClient
import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.impl.server.gateway.demo.http.{JsonPublisher, WebServer}

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

class TradingBot(publisher: TradingEngineClient) {

  class ExecutionReportConsumer extends StreamConsumer[ExecutionReportEvent] {

    override def onUpdate(value: ExecutionReportEvent) = {
      //println(s"EXECUTION REPORT UPDATE ${value.instrumentId()}")
    }

    override def onFlush() = {
      //println(s"onFlush()")

    }

    override def onClose() = {

    }
  }


  def execute(count: Int) = {
    for(i <- 1 to 11) {
      Await.result(publisher.createInstrument(_.instrumentId(i).scale(2))
        .call(_ => {
          println("Successfully created instrument")
        }), FiniteDuration(10, TimeUnit.SECONDS))
    }

    val jsonPublisher = new JsonPublisher(100)
    val server = new WebServer(4444, jsonPublisher)

    server.start()
    publisher.subscribeOrderBook(_.instrumentId(1)).start(jsonPublisher)
    publisher.subscribeExecutionReport(_.instrumentId(1)).start(new ExecutionReportConsumer())
    val random = new java.util.Random()
    for (i: Int <- 1 to count) {
      val call = publisher.placeOrder(
        _.userId(random.nextInt(100)) // 100 users
          .instrumentId(random.nextInt(10) + 1)
          .time(System.currentTimeMillis())
          .order(
            _.price(random.nextInt(50) + 100)
              .quantity(random.nextInt(50) + 100)
              .orderId(s"order${i}")
              .side(if(random.nextBoolean()) Side.BUY else Side.SELL)
              .orderType(OrderType.LIMIT)))


      if(i % 10 == 0) {
        Thread.sleep(1)
      }
      handle(call)
    }
  }

  private def handle(call: Call[PlaceOrderResponse]) = {
    call.call(response => response.time()).onComplete {
      case Success(time) => {
        //println(s"response OK")
      }
      case Failure(cause) => {
        cause.printStackTrace()
      }
    }(ExecutionContext.global)
  }
}
