package trumid.poc.impl.server.demo.book

import org.scalatest._
import trumid.poc.example.commands.{OrderType, Side}

import scala.collection.mutable

class OrderBookTest extends FlatSpec with Matchers {
  val instrument = Instrument(1, PriceScale.DEFAULT)

  it should "fill orders when they match on price" in {
    val listener = new OrderListener(
      Seq(
        "PASSIVE: BUY 10@100.1",
        "FULL FILL: BUY 10@100.1",
        "FULL FILL: SELL 10@100.1",
      )
    )

    val book = new OrderBook(instrument, listener)
    val buyPassiveOrder = new Order(1, 2, Side.BUY, OrderType.LIMIT, Price(100.1), 10)
    val sellAgressiveOrder = new Order(2, 2, Side.SELL, OrderType.LIMIT, Price(100.1), 10)

    book.placeOrder(buyPassiveOrder)
    book.placeOrder(sellAgressiveOrder)
  }

  it should "fill at average price on multiple orders" in {
    val listener = new OrderListener(
      Seq(
        "PASSIVE: BUY 1@100.0",
        "PASSIVE: BUY 2@90.0",
        "FULL FILL: BUY 1@100.0",
        "FULL FILL: BUY 2@90.0",
        s"PARTIAL FILL: SELL 3@${PriceScale.DEFAULT.toPrice((100.0 + 90.0 + 90.0) / 3).toDouble()}",
        "PASSIVE: SELL 7@80.0",
      )
    )

    val book = new OrderBook(instrument, listener)
    val buyPassiveOrder1 = new Order(1, 1, Side.BUY, OrderType.LIMIT, Price(100.0), 1)
    val buyPassiveOrder2 = new Order(2, 2, Side.BUY, OrderType.LIMIT, Price(90.0), 2)
    val sellAgressiveOrder = new Order(3, 3, Side.SELL, OrderType.LIMIT, Price(80), 10)

    book.placeOrder(buyPassiveOrder1)
    book.placeOrder(buyPassiveOrder2)
    book.placeOrder(sellAgressiveOrder)
  }

  it should "cancel market order after fill" in {
    val listener = new OrderListener(
      Seq(
        "PASSIVE: BUY 1@100.0",
        "PASSIVE: BUY 2@90.0",
        "FULL FILL: BUY 1@100.0",
        "FULL FILL: BUY 2@90.0",
        s"PARTIAL FILL: SELL 3@${PriceScale.DEFAULT.toPrice((100.0 + 90.0 + 90.0) / 3).toDouble()}",
        "CANCEL: SELL 7@80.0",
      )
    )

    val book = new OrderBook(instrument, listener)
    val buyPassiveOrder1 = new Order(1, 1, Side.BUY, OrderType.LIMIT, Price(100.0), 1)
    val buyPassiveOrder2 = new Order(2, 2, Side.BUY, OrderType.LIMIT, Price(90.0), 2)
    val sellAgressiveOrder = new Order(3, 3, Side.SELL, OrderType.MARKET, Price(80), 10)

    book.placeOrder(buyPassiveOrder1)
    book.placeOrder(buyPassiveOrder2)
    book.placeOrder(sellAgressiveOrder)
  }

  it should "remove all orders" in {
    val listener = new OrderListener(
      Seq(
        "PASSIVE: BUY 1@100.0",
        "PASSIVE: BUY 2@90.0",
        "PASSIVE: BUY 2@80.0",
        "CANCEL: BUY 2@80.0",
        "CANCEL: BUY 2@90.0",
      )
    )

    val book = new OrderBook(instrument, listener)
    val buyPassiveOrder1 = new Order(1, 1, Side.BUY, OrderType.LIMIT, Price(100.0), 1)
    val buyPassiveOrder2 = new Order(2, 2, Side.BUY, OrderType.LIMIT, Price(90.0), 2)
    val buyPassiveOrder3 = new Order(2, 3, Side.BUY, OrderType.LIMIT, Price(80.0), 2)

    book.placeOrder(buyPassiveOrder1)
    book.placeOrder(buyPassiveOrder2)
    book.placeOrder(buyPassiveOrder3)
    book.removeAllOrders(2)
  }


  private class OrderListener(assertions: Seq[String] = Seq.empty) extends OrderChannel {
    private val queue = new mutable.Queue[String]() ++ assertions

    override def onFill(fill: Fill): Unit = {
      val action = s"${fill.fillType} FILL: ${fill.order.side} ${fill.quantity}@${fill.price}"

      println(action)
      queue.dequeue() shouldBe action
    }

    override def onPassive(order: Order): Unit = {
      val action = s"PASSIVE: ${order.side} ${order.remainingQuantity}@${order.price}"

      println(action)
      queue.dequeue() shouldBe action
    }

    override def onCancel(order: Order): Unit = {
      val action = s"CANCEL: ${order.side} ${order.remainingQuantity}@${order.price}"

      println(action)
      queue.dequeue() shouldBe action
    }
  }
}
