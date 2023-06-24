package trumid.poc.impl.server.message

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
  def compose(code: Byte): T
  def commit(consumer: MessageConsumer[T], userId: Int, correlationId: Long): MessageComposer[T]
}

trait MessageConsumer[B] {
  def consume(frame: MessageFrame, body: B): Unit
}
