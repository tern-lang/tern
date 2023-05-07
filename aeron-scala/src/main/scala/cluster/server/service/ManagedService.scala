package cluster.server.service

import io.aeron.Image
import io.aeron.cluster.service.{Cluster, ClusteredService}

abstract class ManagedService[T] extends ClusteredService {

  override def onStart(cluster: Cluster, snapshotImage: Image): Unit =
    onStart(cluster.asInstanceOf[ManagedCluster], snapshotImage)

  def onStart(cluster: ManagedCluster, snapshotImage: Image): Unit

  def onReady(cluster: ManagedCluster): Unit

  def onState(consumer: (T) => Unit): Unit
}
