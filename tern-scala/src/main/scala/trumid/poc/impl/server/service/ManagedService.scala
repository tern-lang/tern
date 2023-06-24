package trumid.poc.impl.server.service

import io.aeron.cluster.codecs.CloseReason
import io.aeron.cluster.service.{ClientSession, Cluster, ClusteredService}
import io.aeron.{ExclusivePublication, Image}

abstract class ManagedService[T] extends ClusteredService {

  override def onStart(cluster: Cluster, snapshotImage: Image): Unit =
    onStart(cluster.asInstanceOf[ManagedCluster], snapshotImage)

  override def onSessionOpen(clientSession: ClientSession, l: Long): Unit = {}
  override def onSessionClose(clientSession: ClientSession, l: Long, closeReason: CloseReason): Unit = {}
  override def onTimerEvent(l: Long, l1: Long): Unit = {}
  override def onTakeSnapshot(exclusivePublication: ExclusivePublication): Unit = {}
  override def onRoleChange(role: Cluster.Role): Unit = {}
  override def onTerminate(cluster: Cluster): Unit = {}
  def onStart(cluster: ManagedCluster, snapshotImage: Image): Unit = {}
  def onReady(cluster: ManagedCluster): Unit = {}
  def onState(consumer: (T) => Unit): Unit = {}

}
