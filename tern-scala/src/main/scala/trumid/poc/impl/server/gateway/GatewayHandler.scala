package trumid.poc.impl.server.gateway

import io.aeron.cluster.codecs.EventCode
import org.agrona.DirectBuffer
import trumid.poc.impl.server.client.ClusterClientHandler

trait GatewayHandler extends ClusterClientHandler {
  def onStart(context: GatewayContext): Unit
  def onContainerMessage(buffer: DirectBuffer, offset: Int, length: Int): Unit

  override def onClusterSessionEvent(correlationId: Long, clusterSessionId: Long, leadershipTermId: Long,
                                     leaderMemberId: Int, code: EventCode, detail: String): Unit = {}

  override def onClusterNewLeader(clusterSessionId: Long, leadershipTermId: Long, leaderMemberId: Int,
                                  memberEndpoints: String): Unit = {}
}
