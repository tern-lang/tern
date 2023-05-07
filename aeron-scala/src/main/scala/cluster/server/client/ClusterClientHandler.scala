package cluster.server.client

import io.aeron.cluster.client.EgressListener
import io.aeron.cluster.codecs.EventCode
import io.aeron.logbuffer.Header
import org.agrona.DirectBuffer

trait ClusterClientHandler extends EgressListener{
  override def onMessage(clusterSessionId: Long, timestamp: Long, buffer: DirectBuffer, offset: Int, length: Int, header: Header): Unit = {
    onClusterMessage(clusterSessionId, timestamp, buffer, offset, length, header)
  }

  override def onSessionEvent(correlationId: Long, clusterSessionId: Long, leadershipTermId: Long, leaderMemberId: Int, code: EventCode, detail: String): Unit = {
    onSessionEvent(correlationId, clusterSessionId, leadershipTermId, leaderMemberId, code, detail)
  }

  override def onNewLeader(clusterSessionId: Long, leadershipTermId: Long, leaderMemberId: Int, ingressEndpoints: String): Unit = {
    onNewLeader(clusterSessionId,leadershipTermId, leaderMemberId, ingressEndpoints)
  }

  def onClusterConnected(session: ClusterSession): Unit = {
    println("onClusterConnected")
  }

  def onClusterDisconnected(session: ClusterSession, cause: Throwable): Unit = {
    println("onClusterDisconnected")
  }

  def onClusterMessage(clusterSessionId: Long, timestamp: Long, buffer: DirectBuffer, offset: Int, length: Int, header: Header): Unit

  def onClusterSessionEvent(correlationId: Long, clusterSessionId: Long, leadershipTermId: Long, leaderMemberId: Int, code: EventCode, detail: String): Unit

  def onClusterNewLeader(clusterSessionId: Long, leadershipTermId: Long, leaderMemberId: Int, memberEndpoints: String): Unit
}
