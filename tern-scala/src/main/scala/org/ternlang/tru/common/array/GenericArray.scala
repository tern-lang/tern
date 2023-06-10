package org.ternlang.tru.common.array

import org.ternlang.tru.common.Chain
import org.ternlang.tru.common.message.{ByteBuffer, Flyweight}

trait GenericArray[G] {
  def get(index: Int): Option[G]
  def iterator(): Iterator[G]
  def size(): Int
}

trait GenericArrayBuilder[G, A] extends GenericArray[G] {
  def add(): A
}


abstract class GenericArrayCodec[G, A](factory: () => Flyweight[_ <: A], convert: A => G, dimensions: Int) extends GenericArrayBuilder[G, A] {
  private val iterable: GenericArrayIterator[G] = new GenericArrayIterator[G]
  private val chain: Chain[A] = new Chain[A](factory, dimensions)

  def assign(buffer: ByteBuffer, offset: Int, length: Int): this.type = {
    chain.assign(buffer, offset, length)
    this
  }

  override def get(index: Int): Option[G] = {
    chain.get(index).map(convert.apply)
  }

  override def iterator(): Iterator[G] = {
    iterable.assign(this)
  }

  override def add(): A = {
    chain.add()
  }

  override def size(): Int = {
    chain.size()
  }

  final class GenericArrayIterator[G] extends Iterator[G] {
    private var array: Option[GenericArray[G]] = None
    private var value: Option[G] = None
    private var index: Int = 0

    def assign(array: GenericArray[G]): Iterator[G] = {
      this.array = Some(array)
      this.value = None
      this.index = 0
      this
    }

    override def next(): G = {
      if(!hasNext) {
        throw new NoSuchElementException("Element not present")
      }
      val next = value.get
      value = None
      next
    }

    override def hasNext: Boolean = {
      if(value.isDefined) {
        true
      } else if(index < array.get.size) {
        value = array.flatMap(_.get(index))
        index += 1
        value.isDefined
      } else {
        false
      }
    }
  }
}

