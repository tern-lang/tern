package trumid.poc.common.message

import scala.reflect._

trait MessageFrame {
  def getHeader: MessageHeader
  def getFrame: Fragment
  def getBody: Fragment
}

trait MessageHeader {
  def getUserId: Int
  def getCorrelationId: Long
  def getLength: Int // header + payload
}

trait MessageComposer[T] {
  def compose(): T
  def commit(consumer: MessageConsumer[T], userId: Int, correlationId: Long): MessageComposer[T]
}

trait MessageConsumer[B] {
  def consume(frame: MessageFrame, body: B): Unit
}

trait MessageBuilder[T] {
  def userId(userId: Int): MessageBuilder[T]
  def correlationId(correlationId: Long): MessageBuilder[T]
  def create(): T
}

class MessageController[T, R](converter: T => R,
                              consumer: MessageConsumer[T],
                              composer: MessageComposer[T]) extends MessageBuilder[R] {
  private var userId: Int = _
  private var correlationId: Long = _

  def userId(userId: Int): MessageBuilder[R] = {
    this.userId = userId
    this
  }

  def correlationId(correlationId: Long): MessageBuilder[R] = {
    this.correlationId = correlationId
    this
  }

  def create(): R = {
    converter.apply(this.composer.compose())
  }

  def commit(): Unit = {
    this.composer.commit(this.consumer, userId, correlationId)
  }
}


