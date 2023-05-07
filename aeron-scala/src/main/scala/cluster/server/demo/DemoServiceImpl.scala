package cluster.server.demo

import cluster.server.group.NodeMember
import cluster.server.service.{ManagedCluster, ManagedService}
import cluster.server.time.{ClusterClock, ClusterScheduler}
import io.aeron.cluster.codecs.CloseReason
import io.aeron.cluster.service.{ClientSession, Cluster}
import io.aeron.logbuffer.Header
import io.aeron.{ExclusivePublication, Image}
import org.agrona.DirectBuffer

class DemoServiceImpl(member: NodeMember, scheduler: ClusterScheduler, clock: ClusterClock) extends ManagedService[String] {

  override def onStart(cluster: ManagedCluster, snapshotImage: Image): Unit = {
    clock.start(cluster)
    scheduler.start(cluster)
  }

  override def onReady(cluster: ManagedCluster): Unit = ???

  override def onState(consumer: String => Unit): Unit = ???

  override def onSessionOpen(clientSession: ClientSession, l: Long): Unit = ???

  override def onSessionClose(clientSession: ClientSession, l: Long, closeReason: CloseReason): Unit = ???

  override def onSessionMessage(clientSession: ClientSession, l: Long, directBuffer: DirectBuffer, i: Int, i1: Int, header: Header): Unit = ???

  override def onTimerEvent(l: Long, l1: Long): Unit = ???

  override def onTakeSnapshot(exclusivePublication: ExclusivePublication): Unit = ???

  override def onRoleChange(role: Cluster.Role): Unit = ???

  override def onTerminate(cluster: Cluster): Unit = ???
}
