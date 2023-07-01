package trumid.poc.common

import org.agrona.collections.Int2ObjectHashMap
import trumid.poc.common.message.{ByteBuffer, ByteSize, Flyweight}

final class Chain[T](factory: () => Flyweight[_ <: T], dimensions: Int) {
  private val pool: Pool[Link[T]] = new Pool[Link[T]](() => new Link[T](Some(factory.apply())), 32)
  private val table: Int2ObjectHashMap[Link[T]] = new Int2ObjectHashMap[Link[T]]()
  private val head: Link[T] = new Link[T](None)
  private var tail: Link[T] = new Link[T](None)
  private var buffer: ByteBuffer = _
  private var offset: Int = _
  private var length: Int = _

  def assign(buffer: ByteBuffer, offset: Int, length: Int): Chain[T] = {
    this.head.assign(
      buffer,
      offset + ByteSize.SHORT_SIZE,
      length - ByteSize.SHORT_SIZE,
      0)

    this.buffer = buffer
    this.offset = offset
    this.length = length
    this.tail = head
    this.index() // maybe this should be lazy
  }

  def size(): Int = {
    buffer.getShort(offset)
  }

  def add(): T = {
    buffer.setCount(offset + ByteSize.SHORT_SIZE + ByteSize.SHORT_SIZE)
    append().get
  }

  private def append(): Option[T] = {
    val distance: Int = (buffer.length - offset) + ByteSize.SHORT_SIZE
    val link: Link[T] = pool.allocate()
    val size: Int = table.size()
    val element = link.assign(buffer, offset + distance, length - distance, size)

    buffer.setShort(offset, (size + 1).shortValue)
    buffer.setShort(tail.start, distance.shortValue)
    buffer.setCount(offset + distance + ByteSize.SHORT_SIZE + dimensions)
    table.put(size, link)
    tail = link
    element
  }

  def get(index: Int): Option[T] = {
    if (index < 0 || index >= table.size) {
      None
    } else {
      table.get(index).value
    }
  }

  def index(): Chain[T] = {
    if (table.isEmpty) {
      val count = buffer.getShort(offset)

      if(count > 0) {
        var prev = head

        for (i <- 0 to count - 1) {
          val link = pool.allocate()
          val start = prev.pointer()

          link.assign(buffer, offset + start, length - start, i)
          table.put(i, link)
          prev = link
        }
      }
    }
    this
  }

  def reset(): Unit = {
    table.values().forEach(_.reset())
    pool.collect()
    table.clear()
    tail = head
  }

  def clear(): Unit = {
    buffer.setShort(offset, 0.shortValue)
    table.values().forEach(_.reset())
    pool.collect()
    table.clear()
    tail = head
  }

  final class Link[T](flyweight: Option[Flyweight[_ <: T]]) {
    var value: Option[_ <: T] = None
    var start: Int = 0
    var index: Int = 0

    def assign(buffer: ByteBuffer, start: Int, length: Int, index: Int): Option[T] = {
      this.value = flyweight.map(_.assign(buffer, start + ByteSize.SHORT_SIZE, length - ByteSize.SHORT_SIZE))
      this.index = index
      this.start = start
      this.value
    }

    def pointer(): Short = {
      buffer.getShort(start)
    }

    def reset(): Unit = {
      flyweight.map(_.reset())
    }

    override def toString(): String = {
      s"${index}@${start}=${value}"
    }
  }
}
