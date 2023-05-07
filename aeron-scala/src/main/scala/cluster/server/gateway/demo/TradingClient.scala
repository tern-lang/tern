package cluster.server.gateway.demo

import cluster.server.demo.api.{CancelOrderCommandCodec, PlaceOrderCommandCodec}
import cluster.server.message.{DirectByteBuffer, MessageConsumer}
import cluster.server.topic.{Topic, TopicMessageComposer}

class TradingClient(consumer: MessageConsumer[_]) {
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
    placeOrderCodec.compose()
      .withOrderId(orderId)
      .withInstrumentId(instrumentId)
      .withQuantity(quantity)
      .withPrice(price)

    placeOrderCodec.commit(consumer.asInstanceOf[MessageConsumer[PlaceOrderCommandCodec]], 1, 1)
  }

  def cancelOrder(orderId: Long): Unit = {
    cancelOrderCodec.compose().withOrderId(orderId)
    cancelOrderCodec.commit(consumer.asInstanceOf[MessageConsumer[CancelOrderCommandCodec]], 1, 1)
  }
}
