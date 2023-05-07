package cluster.server.client.demo

import cluster.server.client.ClusterClientHandler
import io.aeron.cluster.codecs.EventCode
import io.aeron.logbuffer.Header
import org.agrona.DirectBuffer

class TradingClientAdapter extends ClusterClientHandler {
  
  override def onClusterMessage(clusterSessionId: Long, timestamp: Long,
                                buffer: DirectBuffer, offset: Int, length: Int, header: Header): Unit = {
  }

  override def onClusterSessionEvent(correlationId: Long, clusterSessionId: Long, leadershipTermId: Long,
                                     leaderMemberId: Int, code: EventCode, detail: String): Unit = {

  }

  override def onClusterNewLeader(clusterSessionId: Long, leadershipTermId: Long, leaderMemberId: Int,
                                  memberEndpoints: String): Unit = {

  }
}
