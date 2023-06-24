package trumid.poc.impl.server.topic

import TopicMessageHeader.HEADER_SIZE
import trumid.poc.impl.server.message.{ByteBuffer, DirectByteBuffer, Fragment, MessageHeader}

import java.nio.ByteOrder.LITTLE_ENDIAN

class TopicRouter(venue: Byte) extends MessageHeader {
  val buffer = new DirectByteBuffer(LITTLE_ENDIAN, HEADER_SIZE)
  val routes = new Array[TopicStatus](256)
  val header = new TopicMessageHeader().assign(buffer, 0, HEADER_SIZE) // XXX hack for priming when no messages have arrived
  val fragment = new Fragment
  val payload = new Fragment
  val frame = new TopicFrame

  override def getUserId: Int = header.getUserId()

  override def getCorrelationId: Long = header.getCorrelationId()

  override def getLength: Int = header.getLength()

  def register(route: TopicRoute): TopicRouter = {
    val topic = route.getTopic

    if (topic == null) {
      throw new IllegalStateException("Topic must not be null")
    }

    val description = topic.description
    val code = topic.code

    if (code <= 0) {
      throw new IllegalStateException(s"Topic '$description' has an invalid code")
    }

    val existing = routes(code)

    if (existing != null) {
      throw new IllegalStateException(s"Topic '$description' already registered")
    }

    routes(code) = new TopicStatus(route)
    this
  }

  def route(buffer: ByteBuffer, offset: Int, length: Int): Option[TopicStatus] = {
    val venueId = header.assign(buffer, offset, HEADER_SIZE).getVenueId()
    val currentId = this.venue

    if (currentId == 0 || venueId == currentId) {
      val topicId = header.getTopicId
      val route = routes(topicId)

      if (route != null) {
        val start = System.nanoTime()

        try {
          frame.assign(header, buffer, offset, length)
          route.handler.handle(frame, frame.getBody)
        } finally {
          val finish = System.nanoTime()
          val duration = finish - start

          if (route.count % 1000 == 0) {
            route.maximum = 0
            route.minimum = 0
            route.total = 0
            route.count = 0
            route.last = 0
          }

          route.maximum = Math.max(duration, route.maximum)
          route.minimum = Math.min(duration, route.minimum)
          route.total += duration
          route.last = duration
          route.count += 1
        }
        Some(route)
      } else None
    } else None
  }

  class TopicStatus(val handler: TopicRoute) {

    var total: Long = 0
    var maximum: Long = 0
    var minimum: Long = 0
    var last: Long = 0
    var count: Int = 0

    def getTopic(): CharSequence = handler.getTopic.description

    def getMaximum(): Long = maximum

    def getMinimum(): Long = minimum

    def getAverage(): Long = if (count > 0) total / count else 0

    def getLast(): Long = last

    def getCount(): Int = count

    override def toString(): String = getTopic().toString
  }

}
