package cluster.server.demo

import cluster.server.demo.api.{CancelOrderCommand, CancelOrderResponseCodec, PlaceOrderCommand, PlaceOrderResponseCodec}
import cluster.server.message.{DirectByteBuffer, MessageConsumer, MessageFrame, Publisher}
import cluster.server.topic.{Topic, TopicMessageComposer}

class MatchingEngineOutput(publisher: Publisher) {
  private val buffer = DirectByteBuffer()
  private val consumer: MessageConsumer[_] = (frame: MessageFrame, _: Any) => {
    val buffer = frame.getFrame.getBuffer
    val offset = frame.getFrame.getOffset
    val length = frame.getFrame.getLength

    publisher.publish(buffer, offset, length)
  }
  private val placeOrderCodec: TopicMessageComposer[PlaceOrderResponseCodec] = new TopicMessageComposer[PlaceOrderResponseCodec](
    new PlaceOrderResponseCodec,
    buffer,
    Topic.MatchingEngineResponse,
    0)

  private val cancelOrderCodec: TopicMessageComposer[CancelOrderResponseCodec] = new TopicMessageComposer[CancelOrderResponseCodec](
    new CancelOrderResponseCodec,
    buffer,
    Topic.MatchingEngineResponse,
    0)

  def onPlaceOrderSuccess(command: PlaceOrderCommand) = {
    placeOrderCodec.compose(MatchingEngineAdapter.PLACE_ORDER_RESPONSE)
      .withOrderId(command.getOrderId)
      .withTime(command.getTime)
      .withSuccess(true)

    placeOrderCodec.commit(consumer.asInstanceOf[MessageConsumer[PlaceOrderResponseCodec]], 1, 1)
  }

  def onPlaceOrderFailure(command: PlaceOrderCommand) = {
    placeOrderCodec.compose(MatchingEngineAdapter.PLACE_ORDER_RESPONSE)
      .withOrderId(command.getOrderId)
      .withTime(command.getTime)
      .withSuccess(false)

    placeOrderCodec.commit(consumer.asInstanceOf[MessageConsumer[PlaceOrderResponseCodec]], 1, 1)
  }

  def onCancelOrderSuccess(command: CancelOrderCommand) = {
    cancelOrderCodec.compose(MatchingEngineAdapter.CANCEL_ORDER_RESPONSE)
      .withOrderId(command.getOrderId)
      .withSuccess(true)

    cancelOrderCodec.commit(consumer.asInstanceOf[MessageConsumer[CancelOrderResponseCodec]], 1, 1)
  }

  def onCancelOrderFailure(command: CancelOrderCommand) = {
    cancelOrderCodec.compose(MatchingEngineAdapter.CANCEL_ORDER_RESPONSE)
      .withOrderId(command.getOrderId)
      .withSuccess(false)

    cancelOrderCodec.commit(consumer.asInstanceOf[MessageConsumer[CancelOrderResponseCodec]], 1, 1)
  }

  def onExecutionReport(command: PlaceOrderCommand) = {

  }
}
