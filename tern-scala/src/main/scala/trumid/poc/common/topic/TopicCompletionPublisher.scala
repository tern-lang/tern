package trumid.poc.common.topic

import trumid.poc.common.message._

class TopicCompletionPublisher extends Publisher {
  private val handlers = new Array[TopicCompletionHandler](256)
  private val header = new TopicMessageHeader()

  def register(handler: TopicCompletionHandler): TopicCompletionPublisher = {
    val topic = handler.getTopic
    val description = topic.description
    val code = topic.code

    if (handlers(code) != null) {
      throw new IllegalArgumentException(s"Topic $description already registered")
    }
    handlers(code) = handler
    this
  }

  override def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean = {
    val topicId = header.assign(buffer, offset, length).getTopicId()
    val converter = handlers(topicId)

    if(converter != null) {
      converter.complete(buffer, offset,length)
      true
    } else {
      false
    }
  }

  def clear(): Unit = {
    handlers.indices.foreach(i => handlers(i) = null)
  }
}

