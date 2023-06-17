package trumid.poc.common

import trumid.poc.common.message.DirectByteBuffer
import trumid.poc.example._
object OrderCodecTest extends App {
  val buffer = DirectByteBuffer()
  val codec = new OrderCodec()

  codec.assign(buffer, 0, 8192)
    .price(11.2)
    .quantity(1000)
    .symbol("BTC")
    .description(Some("foo"))


  if(codec.price() != 11.2) {
    throw new Error("Price")
  }
  if(codec.quantity() != 1000) {
    throw new Error("Quantity")
  }
  if(codec.symbol().toString() != "BTC") {
    throw new Error("Symbol")
  }
  codec.symbol("FOOBAR")

  if(codec.symbol().toString() != "FOOBAR") {
    throw new Error("Symbol")
  }
  codec.symbol("ETH")

  if(codec.symbol().toString() != "ETH") {
    throw new Error("Symbol")
  }
  if(codec.price() != 11.2) {
    throw new Error("Price")
  }
  if(codec.quantity() != 1000) {
    throw new Error("Quantity")
  }
  if(codec.description().get.toString() != "foo") {
    throw new Error("Description")
  }
  println(codec.stopPrice())
  println(codec.stopPrice(Some(3333.2)).stopPrice())
  println(codec.description())
  println(codec.symbol())
  println(buffer.length())

  codec.user(user => user.userId(1).accountId(22))

  println(codec.user().userId())
  println(codec.user().accountId())
  println(codec.stopPrice())
  println(codec.description())
}
