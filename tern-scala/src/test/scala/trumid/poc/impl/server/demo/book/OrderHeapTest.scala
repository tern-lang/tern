package trumid.poc.impl.server.demo.book

import org.scalatest.{FlatSpec, Matchers}

class OrderHeapTest extends FlatSpec with Matchers {

  it should "remove order by orderId" in {
    val heap = new OrderHeap(Buy.compare)

    heap.offer(new Order(1,"order-1", Buy, Limit, Price(100.1), 10))
    heap.offer(new Order(2,"order-3", Buy, Limit, Price(100.1), 10))
    heap.offer(new Order(1,"order-2", Buy, Limit, Price(100.1), 10))
    heap.offer(new Order(1,"order-7", Buy, Limit, Price(100.1), 10))
    heap.offer(new Order(1,"order-5", Buy, Limit, Price(100.1), 10))

    heap.size shouldBe 5

    heap.remove("order-2")
    heap.remove("order-5")
    heap.remove("order-3")
    heap.remove("order-1")
    heap.remove("order-7")

    heap.size shouldBe 0
  }

  it should "remove maintains order" in {
    val heap = new OrderHeap(Buy.compare)

    heap.offer(new Order(1,"order-1", Buy, Limit, Price(100.1), 10))
    heap.offer(new Order(1,"order-3", Buy, Limit, Price(100.3), 10))
    heap.offer(new Order(1,"order-2", Buy, Limit, Price(100.2), 10))
    heap.offer(new Order(1,"order-7", Buy, Limit, Price(100.7), 10))
    heap.offer(new Order(1,"order-5", Buy, Limit, Price(100.5), 10))

    heap.size shouldBe 5

    heap.remove("order-2")

    heap.size shouldBe 4
    heap.poll().map(_.orderId) shouldBe Some("order-7")
    heap.poll().map(_.orderId) shouldBe Some("order-5")
    heap.poll().map(_.orderId) shouldBe Some("order-3")
    heap.poll().map(_.orderId) shouldBe Some("order-1")
  }

  it should "allow random access" in {
    val heap = new OrderHeap(Buy.compare)

    heap.offer(new Order(1,"order-1", Buy, Limit, Price(100.1), 10))
    heap.offer(new Order(1,"order-3", Buy, Limit, Price(100.3), 10))
    heap.offer(new Order(1,"order-2", Buy, Limit, Price(100.2), 10))
    heap.offer(new Order(1,"order-7", Buy, Limit, Price(100.7), 10))
    heap.offer(new Order(1,"order-5", Buy, Limit, Price(100.5), 10))

    heap.size shouldBe 5

    heap.remove("order-2")

    heap.size shouldBe 4

    heap.get("order-8") shouldBe None
    heap.get("order-9") shouldBe None
    heap.get("order-7").map(_.orderId) shouldBe Some("order-7")
    heap.get("order-5").map(_.orderId) shouldBe Some("order-5")
    heap.get("order-3").map(_.orderId) shouldBe Some("order-3")
    heap.get("order-1").map(_.orderId) shouldBe Some("order-1")
    heap.contains("order-11") shouldBe false
    heap.contains("order-33") shouldBe false
    heap.contains("order-7") shouldBe true
    heap.contains("order-5") shouldBe true
    heap.contains("order-3") shouldBe true
    heap.contains("order-1") shouldBe true
  }

  it should "order by buy side" in {
    val heap = new OrderHeap(Buy.compare)

    heap.offer(new Order(1,"order-1", Buy, Limit, Price(100.1), 10))
    heap.offer(new Order(1,"order-3", Buy, Limit, Price(100.3), 10))
    heap.offer(new Order(1,"order-2", Buy, Limit, Price(100.2), 10))
    heap.offer(new Order(1,"order-7", Buy, Limit, Price(100.7), 10))
    heap.offer(new Order(1,"order-5", Buy, Limit, Price(100.5), 10))

    heap.size shouldBe 5

    heap.poll().map(_.orderId) shouldBe Some("order-7")
    heap.poll().map(_.orderId) shouldBe Some("order-5")
    heap.poll().map(_.orderId) shouldBe Some("order-3")
    heap.poll().map(_.orderId) shouldBe Some("order-2")
    heap.poll().map(_.orderId) shouldBe Some("order-1")
  }

  it should "order by sell side" in {
    val heap = new OrderHeap(Sell.compare)

    heap.offer(new Order(1,"order-1", Sell, Limit, Price(100.1), 10))
    heap.offer(new Order(4,"order-3", Sell, Limit, Price(100.3), 10))
    heap.offer(new Order(1,"order-2", Sell, Limit, Price(100.2), 10))
    heap.offer(new Order(2,"order-7", Sell, Limit, Price(100.7), 10))
    heap.offer(new Order(1,"order-5", Sell, Limit, Price(100.5), 10))

    heap.size shouldBe 5

    heap.poll().map(_.orderId) shouldBe Some("order-1")
    heap.poll().map(_.orderId) shouldBe Some("order-2")
    heap.poll().map(_.orderId) shouldBe Some("order-3")
    heap.poll().map(_.orderId) shouldBe Some("order-5")
    heap.poll().map(_.orderId) shouldBe Some("order-7")
  }
}

