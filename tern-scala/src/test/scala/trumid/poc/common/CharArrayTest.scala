package trumid.poc.common

import trumid.poc.common.array.{CharArrayCodec, DoubleArrayCodec}
import trumid.poc.common.message.DirectByteBuffer

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
