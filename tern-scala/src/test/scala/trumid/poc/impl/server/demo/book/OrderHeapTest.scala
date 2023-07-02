package trumid.poc.impl.server.demo.book

import org.scalatest.{FlatSpec, Matchers}
import trumid.poc.example.commands.{OrderType, Side}

class OrderHeapTest extends FlatSpec with Matchers {

  it should "remove order by orderId" in {
    val heap = new OrderHeap(new OrderComparator(Side.BUY))

    heap.offer(new Order(1,1, Side.BUY, OrderType.LIMIT, Price(100.1), 10))
    heap.offer(new Order(2,3, Side.BUY, OrderType.LIMIT, Price(100.1), 10))
    heap.offer(new Order(1,2, Side.BUY, OrderType.LIMIT, Price(100.1), 10))
    heap.offer(new Order(1,7, Side.BUY, OrderType.LIMIT, Price(100.1), 10))
    heap.offer(new Order(1,5, Side.BUY, OrderType.LIMIT, Price(100.1), 10))

    heap.size shouldBe 5

    heap.remove(2)
    heap.remove(5)
    heap.remove(3)
    heap.remove(1)
    heap.remove(7)

    heap.size shouldBe 0
  }

  it should "remove maintains order" in {
    val heap = new OrderHeap(new OrderComparator(Side.BUY))

    heap.offer(new Order(1,1, Side.BUY, OrderType.LIMIT, Price(100.1), 10))
    heap.offer(new Order(1,3, Side.BUY, OrderType.LIMIT, Price(100.3), 10))
    heap.offer(new Order(1,2, Side.BUY, OrderType.LIMIT, Price(100.2), 10))
    heap.offer(new Order(1,7, Side.BUY, OrderType.LIMIT, Price(100.7), 10))
    heap.offer(new Order(1,5, Side.BUY, OrderType.LIMIT, Price(100.5), 10))

    heap.size shouldBe 5

    heap.remove(2)

    heap.size shouldBe 4
    heap.poll().map(_.orderId) shouldBe Some(7)
    heap.poll().map(_.orderId) shouldBe Some(5)
    heap.poll().map(_.orderId) shouldBe Some(3)
    heap.poll().map(_.orderId) shouldBe Some(1)
  }

  it should "allow random access" in {
    val heap = new OrderHeap(new OrderComparator(Side.BUY))

    heap.offer(new Order(1,1, Side.BUY, OrderType.LIMIT, Price(100.1), 10))
    heap.offer(new Order(1,3, Side.BUY, OrderType.LIMIT, Price(100.3), 10))
    heap.offer(new Order(1,2, Side.BUY, OrderType.LIMIT, Price(100.2), 10))
    heap.offer(new Order(1,7, Side.BUY, OrderType.LIMIT, Price(100.7), 10))
    heap.offer(new Order(1,5, Side.BUY, OrderType.LIMIT, Price(100.5), 10))

    heap.size shouldBe 5

    heap.remove(2)

    heap.size shouldBe 4

    heap.get(8) shouldBe None
    heap.get(9) shouldBe None
    heap.get(7).map(_.orderId) shouldBe Some(7)
    heap.get(5).map(_.orderId) shouldBe Some(5)
    heap.get(3).map(_.orderId) shouldBe Some(3)
    heap.get(1).map(_.orderId) shouldBe Some(1)
    heap.contains(11) shouldBe false
    heap.contains(33) shouldBe false
    heap.contains(7) shouldBe true
    heap.contains(5) shouldBe true
    heap.contains(3) shouldBe true
    heap.contains(1) shouldBe true
  }

  it should "order by buy side" in {
    val heap = new OrderHeap(new OrderComparator(Side.BUY))

    heap.offer(new Order(1,1, Side.BUY, OrderType.LIMIT, Price(100.1), 10))
    heap.offer(new Order(1,3, Side.BUY, OrderType.LIMIT, Price(100.3), 10))
    heap.offer(new Order(1,2, Side.BUY, OrderType.LIMIT, Price(100.2), 10))
    heap.offer(new Order(1,7, Side.BUY, OrderType.LIMIT, Price(100.7), 10))
    heap.offer(new Order(1,5, Side.BUY, OrderType.LIMIT, Price(100.5), 10))

    heap.size shouldBe 5

    heap.poll().map(_.orderId) shouldBe Some(7)
    heap.poll().map(_.orderId) shouldBe Some(5)
    heap.poll().map(_.orderId) shouldBe Some(3)
    heap.poll().map(_.orderId) shouldBe Some(2)
    heap.poll().map(_.orderId) shouldBe Some(1)
  }

  it should "order by sell side" in {
    val heap = new OrderHeap(new OrderComparator(Side.SELL))

    heap.offer(new Order(1,1, Side.SELL, OrderType.LIMIT, Price(100.1), 10))
    heap.offer(new Order(4,3, Side.SELL, OrderType.LIMIT, Price(100.3), 10))
    heap.offer(new Order(1,2, Side.SELL, OrderType.LIMIT, Price(100.2), 10))
    heap.offer(new Order(2,7, Side.SELL, OrderType.LIMIT, Price(100.7), 10))
    heap.offer(new Order(1,5, Side.SELL, OrderType.LIMIT, Price(100.5), 10))

    heap.size shouldBe 5

    heap.poll().map(_.orderId) shouldBe Some(1)
    heap.poll().map(_.orderId) shouldBe Some(2)
    heap.poll().map(_.orderId) shouldBe Some(3)
    heap.poll().map(_.orderId) shouldBe Some(5)
    heap.poll().map(_.orderId) shouldBe Some(7)
  }
}

