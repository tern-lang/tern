package cluster.server.gateway

import cluster.server.client.ClusterClientHandler
import org.agrona.DirectBuffer

trait GatewayHandler extends ClusterClientHandler {

  def onContainerMessage(buffer: DirectBuffer, offset: Int, length: Int): Unit
}
