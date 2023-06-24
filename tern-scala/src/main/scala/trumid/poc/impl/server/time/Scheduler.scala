package trumid.poc.impl.server.time

import io.aeron.cluster.service.Cluster

import java.util.concurrent.TimeUnit

trait Scheduler {
  def start(cluster: Cluster): Unit
  def schedule(event: Long, time: Long, unit: TimeUnit): Unit
  def cancel(event: Long): Unit
  def reset(): Unit = {}
}

