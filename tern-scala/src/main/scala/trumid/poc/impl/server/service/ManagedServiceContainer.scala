package trumid.poc.impl.server.service

import io.aeron.{ExclusivePublication, Image}
import io.aeron.cluster.codecs.CloseReason
import io.aeron.cluster.service.{ClientSession, Cluster, ClusteredService}
import io.aeron.logbuffer.Header
import org.agrona.DirectBuffer
import trumid.poc.impl.server.group.NodeMember

import java.util.concurrent.TimeUnit

class ManagedServiceContainer[T](service: ManagedService[T], member: NodeMember) extends ClusteredService {

  private var managedCluster: ManagedCluster = _

  override def onStart(cluster: Cluster, snapshotImage: Image): Unit = {
    managedCluster = new ManagedCluster(cluster)
    service.onStart(managedCluster, snapshotImage)
  }

  override def onSessionOpen(session: ClientSession, timestamp: Long): Unit = {
    service.onSessionOpen(session, timestamp)
  }

  override def onSessionClose(session: ClientSession, timestamp: Long, closeReason: CloseReason): Unit = {
    service.onSessionClose(session, timestamp, closeReason)
  }

  override def onSessionMessage(
                                 session: ClientSession,
                                 timestamp: Long,
                                 buffer: DirectBuffer,
                                 offset: Int,
                                 length: Int,
                                 header: Header
                               ): Unit = {
    service.onSessionMessage(session, timestamp, buffer, offset, length, header)
  }

  override def onTimerEvent(correlationId: Long, timestamp: Long): Unit = {
    service.onTimerEvent(correlationId, timestamp)
  }

  override def onTakeSnapshot(snapshotPublication: ExclusivePublication): Unit = {
    service.onTakeSnapshot(snapshotPublication)
  }

  override def onRoleChange(role: Cluster.Role): Unit = {
    service.onRoleChange(role)
  }

  override def onTerminate(cluster: Cluster): Unit = {
    service.onTerminate(cluster)
  }

  override def onNewLeadershipTermEvent(
                                         leadershipTermId: Long,
                                         logPosition: Long,
                                         timestamp: Long,
                                         termBaseLogPosition: Long,
                                         leaderMemberId: Int,
                                         logSessionId: Int,
                                         timeUnit: TimeUnit,
                                         appVersion: Int
                                       ): Unit = {
    if (managedCluster != null) {
      service.onReady(managedCluster)
      managedCluster = null
    }
  }
}
