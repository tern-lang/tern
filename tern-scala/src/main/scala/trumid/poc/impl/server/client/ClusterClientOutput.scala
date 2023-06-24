package trumid.poc.impl.server.client

import org.agrona.DirectBuffer
import trumid.poc.common.message._

trait ClusterClientOutput {
  def publish(buffer: DirectBuffer, offset: Int, length: Int): Boolean
  def consume[T](): MessageConsumer[T] = (frame: MessageFrame, _: T) => {
    frame.getFrame.getBuffer.getBytes(
      frame.getFrame.getOffset,
      (buffer, offset, length) => publish(buffer, offset, length),
      frame.getFrame.getLength)
  }
}
