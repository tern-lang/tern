package trumid.poc.common.message

trait Publisher {
  def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean
  def consume[T](): MessageConsumer[T] = (frame: MessageFrame, _: T) => {
    val buffer = frame.getFrame.getBuffer
    val offset = frame.getFrame.getOffset
    val length = frame.getFrame.getLength

    publish(buffer, offset, length)
  }
}

