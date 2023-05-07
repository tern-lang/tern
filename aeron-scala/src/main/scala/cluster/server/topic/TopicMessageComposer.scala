package cluster.server.topic

import cluster.server.message._
import cluster.server.topic.TopicMessageHeader.HEADER_SIZE
import java.lang.Integer._

class TopicMessageComposer[M](body: Flyweight[_ <: M], buffer: ByteBuffer, topic: Topic, venueId: Byte) extends MessageComposer[M] {
  private val header = new TopicMessageHeader
  private val frame = new TopicFrame
  private var message: Option[M] = None

  override def compose: M = {
    buffer.clear()
    message = Some(body.assign(buffer, HEADER_SIZE, MAX_VALUE))
    message.get
  }

  override def commit(consumer: MessageConsumer[M], target: MessageHeader): MessageComposer[M] = {
    val length = buffer.length()

    if (length == 0) {
      throw new IllegalStateException("Message is empty")
    }
    if (message.isEmpty) {
      throw new IllegalStateException("Message has not been composed")
    }
    val userId = target.getUserId
    val correlationId = target.getCorrelationId

    header.assign(buffer, 0, HEADER_SIZE)
      .withUserId(userId)
      .withCorrelationId(correlationId)
      .withVenueId(venueId)
      .withTopicId(topic.code)

    consumer.consume(frame, message.get)
    message = None
    this
  }
}
