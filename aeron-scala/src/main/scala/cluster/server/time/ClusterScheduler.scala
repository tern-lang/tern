package cluster.server.time

import io.aeron.cluster.service.Cluster

import java.util.concurrent.TimeUnit

class ClusterScheduler extends Scheduler {
  private var cluster: Cluster = _

  def start(cluster: Cluster): Unit = {
    if (cluster == null) {
      throw new IllegalStateException("Cluster must not be null")
    }
    this.cluster = cluster
  }

  override def schedule(event: Long, deadline: Long, unit: TimeUnit): Unit = {
    val time = unit.toMillis(deadline)

    if (cluster == null) {
      throw new IllegalStateException("Cluster is not available")
    }
    cluster.scheduleTimer(event, time)
  }

  override def cancel(event: Long): Unit = {
    if (cluster == null) {
      throw new IllegalStateException("Cluster is not available")
    }
    cluster.cancelTimer(event)
  }
}