package trumid.poc.common

import java.util.function.Supplier
import scala.reflect.ClassTag

final class Pool[T: ClassTag](val factory: Supplier[T], val capacity: Int) {
  private var allocated = new Array[T](capacity)
  private var count = 0
  private var index = 0

  def allocate(): T = {
    if (index < count) {
      index += 1
      return allocated(index - 1)
    }
    val value = factory.get()

    if (count >= allocated.length) {
      grow()
    }
    index += 1
    count += 1
    allocated(index - 1) = value
    value
  }

  private def grow(): Unit = {
    val array = new Array[T](allocated.length * 2)

    if (count > 0) {
      System.arraycopy(allocated, 0, array, 0, count)
    }
    allocated = array
  }

  def collect(): Unit = {
    index = 0
  }
}

