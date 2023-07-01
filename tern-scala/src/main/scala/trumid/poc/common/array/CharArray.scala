package trumid.poc.common.array

import trumid.poc.common.array.CharArrayCodec.CharValueCodec
import trumid.poc.common.message.{ByteBuffer, ByteSize, Flyweight}

import java.lang.{StringBuilder => StringWrapper}

trait CharArray extends GenericArray[Char] with CharSequence {}

trait CharArrayBuilder extends CharArray with GenericArrayBuilder[Char, CharValue] {
  def append(value: Char): CharArrayBuilder
  def append(value: CharSequence): CharArrayBuilder
  def reset(): CharArrayBuilder
  def clear(): CharArrayBuilder
}

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

    override def toString(): String = {
      buffer.getChar(offset).toString
    }
  }
}

final class CharArrayCodec
  extends GenericArrayCodec[Char, CharValue](() => new CharValueCodec, value => value.get, ByteSize.CHAR_SIZE)
    with CharArrayBuilder
    with Flyweight[CharArrayCodec]
    with CharSequence {

  override def append(value: Char): CharArrayCodec = {
    add().set(value)
    this
  }

  override def append(value: CharSequence): CharArrayCodec = {
    for(index <- 0 to value.length - 1) {
      add().set(value.charAt(index))
    }
    this
  }

  override def charAt(index: Int): Char = {
    val size = length();
    val next = get(index)

    if(next.isEmpty) {
      throw new IllegalStateException(s"Character missing at ${index} of ${size}")
    }
    next.get
  }

  override def subSequence(start: Int, end: Int): CharSequence = {
    toString().subSequence(start, end)
  }

  override def length(): Int = {
    size()
  }

  override def reset(): CharArrayCodec = {
    chain.reset()
    this
  }

  override def clear(): CharArrayCodec = {
    chain.clear()
    this
  }

  override def toString(): String = {
    new StringWrapper(this).toString
  }
}