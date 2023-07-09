package trumid.poc.impl.server.gateway.demo.http

import org.scalatest.{FlatSpec, Matchers}
import trumid.poc.common.message.DirectByteBuffer
import trumid.poc.example.events.OrderBookUpdateEventCodec

class OrderBookPublisherTest extends FlatSpec with Matchers {

  it should "ensure we publish correct changes" in {
    val publisher = new JsonPublisher(0)
    val buffer = DirectByteBuffer()
    val codec = new OrderBookUpdateEventCodec(true)

    codec.assign(buffer.clear(), 0, 8192).reset()
      .instrumentId(111)
      .bids(array => {
        array.add().orderId(1).price(11).quantity(11).changeQuantity(11)
        array.add().orderId(2).price(12).quantity(11).changeQuantity(11)
        array.add().orderId(22).price(12).quantity(11).changeQuantity(1)
        array.add().orderId(3).price(33).quantity(1).changeQuantity(1)
      })

    publisher.onUpdate(codec)
    publisher.onJoin(update => {
      println(update)
    })
    publisher.onFlush()

    codec.assign(buffer.clear(), 0, 8192).reset()
      .instrumentId(111)
      .bids(array => {
        array.add().orderId(35).price(12).quantity(11).changeQuantity(-12)
      })
    publisher.onFlush()
    publisher.onUpdate(codec)
    publisher.onFlush()
    publisher.onFlush()
    publisher.onJoin(update => {
      println(update)
    })
    publisher.onFlush()
  }
}