package trumid.poc.impl.server.gateway

import org.agrona.DirectBuffer
import trumid.poc.impl.server.client.ClusterClientHandler

trait GatewayHandler extends ClusterClientHandler {
  def onStart(context: GatewayContext): Unit
  def onContainerMessage(buffer: DirectBuffer, offset: Int, length: Int): Unit
}
