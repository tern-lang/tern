package org.ternlang.tru.common

import org.ternlang.tru.common.array.DoubleArrayCodec
import org.ternlang.tru.common.message.DirectByteBuffer

object DoubleArrayTest extends App {
  val buffer = DirectByteBuffer()
  val codec = new DoubleArrayCodec()

  codec.assign(buffer, 0, 1024)
  codec.add().set(22.0d)
  codec.add().set(23.0d)
  codec.add().set(24.0d)

  if(codec.size != 3) {
    throw new Error
  }
  codec.iterator().foreach(d => println(d))

}
