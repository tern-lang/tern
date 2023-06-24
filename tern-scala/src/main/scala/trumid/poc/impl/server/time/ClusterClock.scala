package trumid.poc.impl.server.time

import io.aeron.cluster.service.Cluster

import java.util.concurrent.TimeUnit

class ClusterClock extends Clock {
  private var cluster: Cluster = _

  def start(cluster: Cluster): Unit = {
    if (cluster == null) {
      throw new IllegalStateException("Cluster must not be null")
    }
    this.cluster = cluster
  }

  override def currentTime(): Long = {
    if (cluster == null) {
      throw new IllegalStateException("Cluster is not available")
    }
    val time: Long = cluster.time
    val timeUnit: TimeUnit = cluster.timeUnit
    TimeUnit.MILLISECONDS.convert(time, timeUnit)
  }

  override def currentTime(convertUnit: TimeUnit): Long = {
    if (cluster == null) {
      throw new IllegalStateException("Cluster is not available")
    }
    val time: Long = cluster.time
    val timeUnit: TimeUnit = cluster.timeUnit
    convertUnit.convert(time, timeUnit)
  }
}