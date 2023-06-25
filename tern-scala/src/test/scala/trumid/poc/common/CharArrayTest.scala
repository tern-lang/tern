package trumid.poc.common

import trumid.poc.common.array.CharArrayCodec
import trumid.poc.common.message.DirectByteBuffer

object CharArrayTest extends App {
  val buffer = DirectByteBuffer()
  val codec = new CharArrayCodec()
  val codec2 = new CharArrayCodec()

  codec.assign(buffer, 0, 1024)
  codec.append("Hello this is a test")

  if(codec.charAt(0) != 'H') {
    throw new Error("First char is " + codec.charAt(0))
  }
  if(codec.toString != "Hello this is a test") {
    throw new Error
  }
  codec.reset()
  codec2.assign(buffer, 0, 1024)
  println(codec2)
}
