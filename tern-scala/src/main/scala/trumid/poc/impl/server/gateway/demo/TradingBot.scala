package trumid.poc.impl.server.gateway.demo

import trumid.poc.example.TradingEngineClient
import trumid.poc.example.commands._
import trumid.poc.impl.server.gateway.demo.http.{JsonPublisher, WebServer}

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

class TradingBot(publisher: TradingEngineClient) {
  private val timer = new RoundTripTimer().start()

  def execute(count: Int, throttle: Int) = {
    val jsonPublisher = new JsonPublisher(100)
    val server = new WebServer(4444, jsonPublisher)

    server.start()
    println("Server started!!!")

    for(i <- 1 to 11) {
      Await.result(publisher.createInstrument(_.instrumentId(i).scale(2))
        .call(_ => {
          println("Successfully created instrument")
        }), FiniteDuration(10, TimeUnit.SECONDS))
    }
    publisher.subscribeOrderBook(_.instrumentId(1)).start(jsonPublisher)

    val random = new java.util.Random()
    for (i: Int <- 1 to count) {
      placeOrder(i, throttle, random)

      if(i % 20 == 0) {
        cancelAllOrders(i, random)
      }
    }
  }

  private def cancelAllOrders(i: Int, random: java.util.Random): Unit = {
    val call = publisher.cancelAllOrders(
      _.userId(random.nextInt(10)) // 100 users
        .instrumentId(random.nextInt(10) + 1)
        .time(System.nanoTime()))


    call.call(response => response.time()).onComplete {
      case Success(time) => {
        timer.update(System.nanoTime() - time)
      }
      case Failure(cause) => {
        cause.printStackTrace()
      }
    }(ExecutionContext.global)
  }

  private def placeOrder(i: Int, throttle: Int, random: java.util.Random): Unit = {
    val call = publisher.placeOrder(
      _.userId(random.nextInt(10))
        .instrumentId(random.nextInt(10) + 1)
        .time(System.nanoTime())
        .order(
          _.price(random.nextInt(100) + 2)
            .quantity(random.nextInt(50) + 2)
            .orderId(i)
            .side(if(random.nextBoolean()) Side.BUY else Side.SELL)
            .orderType(OrderType.LIMIT)))


    if(i % throttle == 0) {
      Thread.sleep(1)
    }
    call.call(response => response.time()).onComplete {
      case Success(time) => {
        timer.update(System.nanoTime() - time)
      }
      case Failure(cause) => {
        cause.printStackTrace()
      }
    }(ExecutionContext.global)
  }

  private class RoundTripTimer {
    private val times = new AtomicLong()
    private val count = new AtomicLong()

    def start(): RoundTripTimer = {
      val thread = new Thread(() => run())

      thread.setDaemon(true)
      thread.start()
      this
    }

    def update(nanos: Long): Unit = {
      count.getAndIncrement()
      times.getAndAdd(nanos)
    }

    def run(): Unit = {
      while(true) {
        val totalCount = count.getAndSet(0)
        val totalTime = times.getAndSet(0)

        if(totalCount > 0) {
          println(s"RTT ${TimeUnit.NANOSECONDS.toMicros(totalTime / totalCount)} (${totalCount})")
        }
        Thread.sleep(1000)
      }
    }
  }
}
