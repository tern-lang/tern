package cluster.server.gateway

import cluster.server.client.InputRingBuffer
import org.agrona.MutableDirectBuffer
import org.agrona.concurrent.MessageHandler

class InputPoller(private val input: InputRingBuffer, private val handler: GatewayHandler) {
  private val messageHandler: MessageHandler =
    (_: Int, buffer: MutableDirectBuffer, offset: Int, length: Int) =>
      handler.onContainerMessage(buffer, offset, length)

  def poll(): Int = input.consume(messageHandler, 10)
}
