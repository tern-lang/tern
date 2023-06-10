package org.ternlang.tru.common.array

import org.ternlang.tru.common.array.CharArrayCodec.CharValueCodec
import org.ternlang.tru.common.message.{ByteBuffer, ByteSize, Flyweight}
import java.lang.{StringBuilder => StringWrapper}

trait CharArray extends GenericArray[Char] {}
trait CharArrayBuilder extends CharArray with GenericArrayBuilder[Char, CharValue] {}

trait CharValue {
  def get(): Char
  def set(value: Char): CharValue
}

object CharArrayCodec {

  final class CharValueCodec extends CharValue with Flyweight[CharValueCodec] {
    private var buffer: ByteBuffer = _
    private var offset: Int = _
    private var length: Int = _

    override def assign(buffer: ByteBuffer, offset: Int, length: Int): CharValueCodec = {
      this.buffer = buffer
      this.offset = offset
      this.length = length
      this
    }

    override def get(): Char = {
      buffer.getChar(offset)
    }

    override def set(value: Char): CharValue = {
      buffer.setChar(offset, value)
      this
    }
  }
}

final class CharArrayCodec
  extends GenericArrayCodec[Char, CharValue](() => new CharValueCodec, value => value.get, ByteSize.CHAR_SIZE)
    with CharArrayBuilder
    with Flyweight[CharArrayCodec]
    with CharSequence {

  def append(value: Char): CharArrayCodec = {
    add().set(value)
    this
  }

  def append(value: String): CharArrayCodec = {
    value.foreach(_ => add().set(_))
    this
  }

  override def charAt(index: Int): Char = {
    get(index).get
  }

  override def subSequence(start: Int, end: Int): CharSequence = {
    toString().subSequence(start, end)
  }

  override def length(): Int = {
    size()
  }

  override def toString(): String = {
    new StringWrapper(this).toString
  }
}