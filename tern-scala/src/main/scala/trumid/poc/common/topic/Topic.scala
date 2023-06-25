package trumid.poc.common.topic

import trumid.poc.common.message._
import trumid.poc.common.topic.TopicMessageHeader.HEADER_SIZE

case class Topic(code: Byte, description: String) {

  def route(handler: (MessageFrame, Fragment) => Unit): TopicRoute = {
    TopicRouteHandler(this, handler)
  }

  def complete[T](flyweight: Flyweight[T], completion: (MessageHeader) => Unit): TopicCompletionHandler = {
    TopicResponseConverterHandler(this, flyweight, completion)
  }

  private case class TopicRouteHandler(topic: Topic, handler: (MessageFrame, Fragment) => Unit) extends TopicRoute {

    override def handle(frame: MessageFrame, payload: Fragment): Unit = {
      handler.apply(frame, payload)
    }

    override def getTopic: Topic = {
      topic
    }
  }

  private case class TopicResponseConverterHandler[T](topic: Topic,
                                                      flyweight: Flyweight[T],
                                                      completion: (MessageHeader) => Unit) extends TopicCompletionHandler {
    private val header = new TopicMessageHeader()

    override def complete(buffer: ByteBuffer, offset: Int, length: Int): Unit = {
      header.assign(buffer, offset, length)
      flyweight.assign(
        buffer,
        offset + TopicMessageHeader.HEADER_SIZE,
        length - TopicMessageHeader.HEADER_SIZE)

      completion.apply(header)
    }

    override def getTopic: Topic = {
      topic
    }
  }
}

trait TopicRoute {
  def handle(frame: MessageFrame, payload: Fragment): Unit
  def getTopic: Topic
}

trait TopicCompletionHandler {
  def complete(buffer: ByteBuffer, offset: Int, length: Int): Unit
  def getTopic: Topic
}

class TopicFrame extends MessageFrame {

  private val fragment = new Fragment()
  private var header: TopicMessageHeader = _
  private var buffer: ByteBuffer = _
  private var offset: Int = _
  private var length: Int = _

  def assign(header: TopicMessageHeader, buffer: ByteBuffer, offset: Int, length: Int): this.type = {
    this.header = header
    this.buffer = buffer
    this.offset = offset
    this.length = length
    this
  }

  def login(userId: Int): MessageFrame = {
    header.withUserId(userId)
    this
  }

  override def getHeader: MessageHeader = this.header

  override def getFrame: Fragment = fragment.assign(buffer, offset, length)

  override def getBody: Fragment = fragment.assign(buffer, offset + HEADER_SIZE, length - HEADER_SIZE)
}

object TopicMessageHeader {
  val USER_OFFSET: Int = 0
  val USER_SIZE: Int = ByteSize.INT_SIZE
  val CORRELATION_ID_OFFSET: Int = USER_OFFSET + USER_SIZE
  val CORRELATION_ID_SIZE: Int = ByteSize.LONG_SIZE
  val VENUE_ID_OFFSET: Int = CORRELATION_ID_OFFSET + CORRELATION_ID_SIZE
  val VENUE_ID_SIZE: Int = ByteSize.BYTE_SIZE
  val TOPIC_ID_OFFSET: Int = VENUE_ID_OFFSET + VENUE_ID_SIZE
  val TOPIC_ID_SIZE: Int = ByteSize.BYTE_SIZE
  val HEADER_SIZE: Int = TOPIC_ID_OFFSET + TOPIC_ID_SIZE
}

class TopicMessageHeader extends MessageHeader with Flyweight[TopicMessageHeader] {

  import TopicMessageHeader._

  private var buffer: ByteBuffer = _
  private var offset: Int = _

  override def assign(buffer: ByteBuffer, offset: Int, length: Int): TopicMessageHeader = {
    if (length < HEADER_SIZE) {
      throw new IllegalArgumentException(s"Length must be at least $HEADER_SIZE")
    }
    this.buffer = buffer
    this.offset = offset
    this
  }

  override def getUserId(): Int = buffer.getInt(offset + USER_OFFSET)

  def withUserId(userId: Int): TopicMessageHeader = {
    buffer.setCount(offset + HEADER_SIZE)
    buffer.setInt(offset + USER_OFFSET, userId)
    this
  }

  override def getCorrelationId(): Long = buffer.getLong(offset + CORRELATION_ID_OFFSET)

  def withCorrelationId(correlationId: Long): TopicMessageHeader = {
    buffer.setCount(offset + HEADER_SIZE)
    buffer.setLong(offset + CORRELATION_ID_OFFSET, correlationId)
    this
  }

  def getVenueId(): Byte = buffer.getByte(offset + VENUE_ID_OFFSET)

  def withVenueId(venueId: Byte): TopicMessageHeader = {
    buffer.setCount(offset + HEADER_SIZE)
    buffer.setByte(offset + VENUE_ID_OFFSET, venueId)
    this
  }

  def getTopicId(): Byte = buffer.getByte(offset + TOPIC_ID_OFFSET)

  def withTopicId(topicId: Byte): TopicMessageHeader = {
    buffer.setCount(offset + HEADER_SIZE)
    buffer.setByte(offset + TOPIC_ID_OFFSET, topicId)
    this
  }

  override def getLength(): Int = {
    val total = buffer.length()
    val start = buffer.getInt(offset)

    if (start > 0) start else total
  }
}

