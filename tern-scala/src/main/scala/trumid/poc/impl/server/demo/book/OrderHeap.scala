package trumid.poc.impl.server.demo.book

import org.agrona.collections.Hashing.DEFAULT_LOAD_FACTOR
import org.agrona.collections._

import java.util
import java.util.Comparator
import java.util.stream.Stream

object OrderHeap {
  private val DEFAULT_INITIAL_CAPACITY = 65536
}

class OrderHeap(val comparator: Comparator[Order]) {
  private val orders = new Long2ObjectHashMap[OrderEntry](OrderHeap.DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true)
  private val users = new Int2ObjectHashMap[LongHashSet](OrderHeap.DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true)
  private var queue = new Array[OrderEntry](OrderHeap.DEFAULT_INITIAL_CAPACITY)
  private var count = 0

  def offer(order: Order): Unit = {
    val orderId = order.orderId()

    if (contains(orderId)) {
      throw new IllegalStateException("Order " + orderId + " already exists")
    }
    append(order)
    moveUp(count - 1)
  }

  def peek(): Option[Order] = {
    if (count > 0) {
      Some(queue(0).order)
    } else {
      None
    }
  }

  def poll(): Option[Order] = {
    if (count > 0) {
      val top = queue(0)

      count -= 1;
      swap(0, count)
      moveDown(0)
      Some(top.remove)
    } else {
      None
    }
  }

  def get(orderId: Long): Option[Order] = {
    val entry = orders.get(orderId)

    if (entry != null) {
      Some(entry.order)
    } else {
      None
    }
  }

  def remove(orderId: Long): Option[Order] = {
    val entry = orders.remove(orderId)

    if (entry != null) {
      val index = entry.index

      count -= 1;

      if (index != count) {
        swap(index, count) // move down from removal position
        moveDown(index)
      }
      Some(entry.remove)
    } else {
      None
    }
  }

  def stream(): Stream[Order] = {
    orders.values().stream().map(entry => entry.get())
  }

  def stream(userId: Int): Stream[Order] = {
    orders(userId).stream().map(orderId => orders.get(orderId).get())
  }

  def contains(orderId: Long): Boolean = {
    orders.containsKey(orderId)
  }

  private def append(order: Order): Unit = {
    val index = count

    count += 1

    if (queue.length <= index) {
      expandCapacity()
    }
    val entry = new OrderEntry(order, index)

    queue(index) = entry

    if (orders.put(entry.get.orderId, entry) == null) {
      orders(entry.get.userId).add(entry.get.orderId)
    }
  }

  private def orders(userId: Int): LongHashSet = {
    var orderIds = users.get(userId)

    if (orderIds == null) {
      orderIds = new LongHashSet(OrderHeap.DEFAULT_INITIAL_CAPACITY,
        DEFAULT_LOAD_FACTOR,
        true)
      users.put(userId, orderIds)
    }
    orderIds
  }

  private def expandCapacity(): Unit = {
    queue = util.Arrays.copyOf(queue, queue.length * 2)
  }

  private def moveUp(pos: Int): Unit = {
    val parent = (pos - 1) / 2

    if (parent != pos) {
      if (compare(parent, pos) > 0) {
        swap(parent, pos)

        if (parent > 0) {
          moveUp(parent)
        }
      }
    }
  }

  private def moveDown(pos: Int): Unit = {
    val left = (pos * 2) + 1
    val right = left + 1

    if (right < count) {
      val index = if (compare(left, right) < 0) left else right

      if (compare(index, pos) < 0) {
        swap(index, pos)
        moveDown(index)
      }
    } else if (left < count) {
      if (compare(left, pos) < 0) {
        swap(left, pos)
        moveDown(left)
      }
    }
  }

  private def compare(left: Int, right: Int) = {
    val leftEntry = queue(left)
    val rightEntry = queue(right)

    comparator.compare(leftEntry.order, rightEntry.order)
  }

  private def swap(left: Int, right: Int): Unit = {
    val tempEntry = queue(left)

    queue(left) = queue(right)
    queue(right) = tempEntry
    queue(left).move(left)
    queue(right).move(right)
  }

  def isEmpty(): Boolean = count <= 0

  def size(): Int = count

  class OrderEntry(val order: Order, var index: Int) {

    def get(): Order = order

    def move(index: Int): Unit = {
      this.index = index
    }

    def remove(): Order = {
      orders.remove(order.orderId)
      users.get(order.userId).remove(order.orderId)
      queue(index) = null
      order
    }
  }
}
