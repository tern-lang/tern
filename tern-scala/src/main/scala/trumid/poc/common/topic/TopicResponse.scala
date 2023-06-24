package trumid.poc.common.topic
import trumid.poc.common.message._
trait TopicMessageSubscriber {
  def onBegin (message: BeginMessage): Unit
  def onResponse (message: ResponseMessage): Unit
  def onFlush(message: FlushMessage): Unit
}

trait TopicResponse {
  def handle(subscriber: TopicMessageSubscriber): Boolean
}

trait TopicResponseConverter {
  def convert(buffer: ByteBuffer, offset: Int, length: Int): TopicResponse
  def getTopic: Topic
}

class BeginMessage extends TopicResponse {

  override def handle(subscriber: TopicMessageSubscriber): Boolean = {
    subscriber.onBegin(this)
    true
  }
}

class FlushMessage extends TopicResponse {

  override def handle(subscriber: TopicMessageSubscriber): Boolean = {
    subscriber.onFlush(this)
    true
  }
}

class ResponseMessage extends TopicResponse {

  private var payload: Any = null
  private var success: Boolean = false
  private var userId: Int = 0
  private var correlationId: Long = 0L

  def assign(userId: Int, correlationId: Long, payload: Any, success: Boolean): ResponseMessage = {
    this.userId = userId
    this.correlationId = correlationId
    this.payload = payload
    this.success = success
    this
  }

  override def handle(subscriber: TopicMessageSubscriber): Boolean = {
    subscriber.onResponse(this)
    true
  }

  def isSuccess: Boolean = success

  def getUserId: Int = userId

  def getCorrelationId: Long = correlationId

  def getPayload: Any = payload
}