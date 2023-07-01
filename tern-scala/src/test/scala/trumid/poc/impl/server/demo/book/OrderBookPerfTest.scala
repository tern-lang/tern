package trumid.poc.impl.server.demo.book

import org.scalatest.{FlatSpec, Matchers}

import java.util.Random
import java.util.concurrent.TimeUnit

class OrderBookPerfTest extends FlatSpec with Matchers {

  it should "verify performance" in {
    val book = new OrderBook(Instrument(1, PriceScale.DEFAULT), new OrderListener())
    val random = new Random()

    book.placeOrder(new Order(
      0,
      s"order-0",
      if(random.nextBoolean()) Buy else Sell,
      Limit,
      Price(random.nextInt(100) + 100), 10))

    val start = System.nanoTime()
    val count = 1000000

    for(i <- 1 to count) {
      book.placeOrder(new Order(
        random.nextInt(100),
        s"order-${i}",
        if(random.nextBoolean()) Buy else Sell,
        Limit,
        Price(random.nextInt(100) + 100), 10))
    }
    val micros = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - start)
    println(s"ms=${micros/1000} micros=${micros} micros-per-order=${micros/count}")
  }

  private class OrderListener extends OrderChannel {

    override def onFill(fill: Fill): Unit = {
    }

    override def onPassive(order: Order): Unit = {
    }

    override def onCancel(order: Order): Unit = {
    }
  }
}
