package trumid.poc.common.message

trait Publisher {
  def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean
  def consume[T](): MessageConsumer[T] = (frame: MessageFrame, _: T) => {
    publish(
      frame.getFrame.getBuffer,
      frame.getFrame.getOffset,
      frame.getFrame.getLength)
  }
}

