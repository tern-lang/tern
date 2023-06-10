package org.ternlang.tru.common

trait GenericArray[G] {
  def get(index: Int): Option[G]
  def iterator(): Iterator[G]
  def size(): Int
}

trait GenericArrayBuilder[G, A] extends GenericArray[G] {
  def add(): A
  def add(value: G): A
  def clear(): GenericArrayBuilder[G, A]
}

class GenericArrayIterator[G] extends Iterator[G] {
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
    value.get
  }

  override def hasNext: Boolean = {
    if(value.isDefined) {
      true
    } else if(index < array.size) {
      value = array.flatMap(_.get(index))
      index += 1
      value.isDefined
    } else {
      false
    }
  }
}

