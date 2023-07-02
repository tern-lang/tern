package trumid.poc.common

import org.scalatest.{FlatSpec, Matchers}
import trumid.poc.common.message.DirectByteBuffer
import trumid.poc.example.events.OrderBookUpdateEventCodec

class OrderBookUpdateEventTest extends FlatSpec with Matchers {

  it should "handle orderId properly" in {
    val buffer = DirectByteBuffer()
    val writer = new OrderBookUpdateEventCodec(true)
    val reader = new OrderBookUpdateEventCodec(true)

    writer.assign(buffer.clear(), 0, 8192).bids(array => {
      array.add().orderId(1).price(11).quantity(11)
    })

    reader.assign(buffer, 0, 8192)
      .reset()
      .bids()
      .iterator().foreach(bid => println(bid.orderId()))

    writer.assign(buffer.clear(), 0, 8192).reset().bids(array => {
      array.add().orderId(111).price(11).quantity(11)
    })

    reader.assign(buffer, 0, 8192)
      .reset()
      .bids()
      .iterator().foreach(bid => println(bid.orderId()))
  }
}
