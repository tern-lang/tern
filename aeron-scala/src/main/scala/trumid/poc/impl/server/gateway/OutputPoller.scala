package trumid.poc.impl.server.gateway

import org.agrona.DirectBuffer
import org.agrona.concurrent.MessageHandler
import trumid.poc.impl.server.client.OutputRingBuffer
import trumid.poc.impl.server.message.{DirectBufferWrapper, Publisher}

class OutputPoller(private val output: OutputRingBuffer, private val publisher: Publisher) {
  private val wrapper = new DirectBufferWrapper()
  private val handler: MessageHandler =
    (_: Int, buffer: DirectBuffer, offset: Int, length: Int) => {
      wrapper.wrap(buffer, offset, length)
      publisher.publish(wrapper, 0, length)
    }

  def poll(): Int = output.consume(handler, 10)
}