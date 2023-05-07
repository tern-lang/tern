package cluster.server.topic

import cluster.server.message.{ByteBuffer, Publisher}

class TopicMessageConsumer(subscriber: TopicMessageSubscriber) extends Publisher {
  private val converters = new Array[TopicResponseConverter](256)
  private val header = new TopicMessageHeader()

  def register(converter: TopicResponseConverter): Unit = {
    val topic = converter.getTopic
    val description = topic.description
    val code = topic.code

    if (converters(code) != null) {
      throw new IllegalArgumentException(s"Topic $description already registered")
    }

    converters(code) = converter
  }

  override def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean = {
    val topicId = header.assign(buffer, offset, length).getTopicId()
    val converter = converters(topicId)
    val event = if (converter != null) {
      converter.convert(buffer, offset, length)
    } else {
      new ResponseMessage().assign(
        header.getUserId(),
        header.getCorrelationId(),
        null,
        false)
    }

    event.handle(subscriber)
    true
  }

  def flush(): Unit = {
    new FlushMessage().handle(subscriber)
  }

  def clear(): Unit = {
    converters.indices.foreach(i => converters(i) = null)
  }
}

