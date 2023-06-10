package org.ternlang.tru.common

import org.ternlang.tru.common.array.{CharArrayCodec, DoubleArrayCodec}
import org.ternlang.tru.common.message.DirectByteBuffer

object CharArrayTest extends App {
  val buffer = DirectByteBuffer()
  val codec = new CharArrayCodec()

  codec.assign(buffer, 0, 1024)
  codec.add().set('H')
  codec.add().set('e')
  codec.add().set('l')
  codec.add().set('l')
  codec.add().set('o')

  if(codec.toString != "Hello") {
    throw new Error
  }
  println(codec)
}
