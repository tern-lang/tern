package trumid.poc.impl.server.gateway

import org.agrona.MutableDirectBuffer
import org.agrona.concurrent.MessageHandler
import trumid.poc.impl.server.client.InputRingBuffer

class InputPoller(private val input: InputRingBuffer, private val handler: GatewayHandler) {
  private val messageHandler: MessageHandler =
    (_: Int, buffer: MutableDirectBuffer, offset: Int, length: Int) =>
      handler.onContainerMessage(buffer, offset, length)

  def poll(): Int = input.consume(messageHandler, 10)
}
