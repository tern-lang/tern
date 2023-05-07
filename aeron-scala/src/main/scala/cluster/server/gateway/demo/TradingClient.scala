package cluster.server.gateway.demo

import cluster.server.demo.MatchingEngineAdapter
import cluster.server.demo.api.{CancelOrderCommandCodec, PlaceOrderCommandCodec}
import cluster.server.message.{DirectByteBuffer, MessageConsumer}
import cluster.server.topic.{Topic, TopicMessageComposer}

import java.util.concurrent.TimeUnit

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

  def placeOrder(orderId: Long, instrumentId: Int, quantity: Long, price: Double, time: Long): Unit = {
    val command = placeOrderCodec.compose(MatchingEngineAdapter.PLACE_ORDER)
      .withOrderId(orderId)
      .withInstrumentId(instrumentId)
      .withQuantity(quantity)
      .withPrice(price)
      .withTime(time)

    if(command.getOrderId != orderId) {
      throw new IllegalArgumentException("Invalid order " + command.getOrderId)
    }
    if(command.getInstrumentId != instrumentId) {
      throw new IllegalArgumentException("Invalid instrument " + command.getInstrumentId)
    }
    if(command.getTime != time) {
      throw new IllegalArgumentException("Invalid time " + command.getTime)
    }
    placeOrderCodec.commit(consumer.asInstanceOf[MessageConsumer[PlaceOrderCommandCodec]], 1, 1)
  }

  def cancelOrder(orderId: Long): Unit = {
    val command = cancelOrderCodec.compose(MatchingEngineAdapter.CANCEL_ORDER)
      .withOrderId(orderId)
      .withTime(TimeUnit.NANOSECONDS.toMicros(System.nanoTime()))

    if(command.getOrderId == orderId) {
      throw new IllegalArgumentException("Invalid order " + command.getOrderId)
    }
    cancelOrderCodec.commit(consumer.asInstanceOf[MessageConsumer[CancelOrderCommandCodec]], 1, 1)
  }
}
