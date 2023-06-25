package trumid.poc.common.topic

import trumid.poc.common.message._
import trumid.poc.common.topic.TopicMessageHeader.HEADER_SIZE

import java.lang.Integer._

class TopicMessageComposer[M](body: Flyweight[_ <: M], buffer: ByteBuffer, topic: Byte, venueId: Byte) extends MessageComposer[M] {
  private val header = new TopicMessageHeader
  private val frame = new TopicFrame
  private var message: Option[M] = None

  override def commit(consumer: MessageConsumer[M], userId: Int, correlationId: Long): MessageComposer[M] = {
    val length = buffer.length()

    if (length == 0) {
      throw new IllegalStateException("Message is empty")
    }
    if (message.isEmpty) {
      throw new IllegalStateException("Message has not been composed")
    }
    header.assign(buffer, 0, HEADER_SIZE)
      .withUserId(userId)
      .withCorrelationId(correlationId)
      .withVenueId(venueId)
      .withTopicId(topic)

    frame.assign(header, buffer, 0, length)
    consumer.consume(frame, message.get)
    message = None
    this
  }

  override def compose(): M = {
    buffer.clear()
    buffer.setCount(HEADER_SIZE)
    message = Some(body.assign(buffer, HEADER_SIZE + ByteSize.BYTE_SIZE, MAX_VALUE))
    message.get
  }
}
