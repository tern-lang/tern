package cluster.server.gateway.demo

import cluster.server.client.ClusterClient
import cluster.server.demo.api.{CancelOrderCommandCodec, PlaceOrderCommandCodec}
import cluster.server.message.{DirectByteBuffer, MessageConsumer, MessageFrame}
import cluster.server.topic.{Topic, TopicMessageComposer}

class TradingClient(client: ClusterClient) {
  private val buffer = DirectByteBuffer()
  private val placeOrderCodec: TopicMessageComposer[PlaceOrderCommandCodec] = new TopicMessageComposer[PlaceOrderCommandCodec](
    new PlaceOrderCommandCodec,
    buffer,
    Topic.MatchingEngine,
    0)

  private val cancelOrderCodec: TopicMessageComposer[CancelOrderCommandCodec] = new TopicMessageComposer[CancelOrderCommandCodec](
    new CancelOrderCommandCodec,
    buffer,
    Topic.MatchingEngine,
    0)

  def placeOrder(orderId: Long, instrumentId: Int, quantity: Long, price: Double): Unit = {
    val consumer: MessageConsumer[PlaceOrderCommandCodec] = (frame: MessageFrame, _: PlaceOrderCommandCodec) => {
      val buffer =  frame.getFrame.getBuffer
      val offset =  frame.getFrame.getOffset
      val length = frame.getFrame.getLength

      buffer.getBytes(offset, (a, b, c) => client.input.publish(a, b, c), length)
    }
    placeOrderCodec.compose().withOrderId(orderId)
      .withInstrumentId(instrumentId)
      .withQuantity(quantity)
      .withPrice(price)

    placeOrderCodec.commit(consumer, 1, 1)
  }

  def cancelOrder(orderId: Long): Unit = {
    val consumer: MessageConsumer[CancelOrderCommandCodec] = (frame: MessageFrame, _: CancelOrderCommandCodec) => {
      val buffer =  frame.getFrame.getBuffer
      val offset =  frame.getFrame.getOffset
      val length = frame.getFrame.getLength

      buffer.getBytes(offset, (a, b, c) => client.input.publish(a, b, c), length)
    }
    cancelOrderCodec.compose().withOrderId(orderId)
    cancelOrderCodec.commit(consumer, 1, 1)
  }
}
