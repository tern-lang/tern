package trumid.poc.impl.server.client

import io.aeron.Aeron
import io.aeron.cluster.client.AeronCluster
import io.aeron.cluster.client.AeronCluster.AsyncConnect
import org.agrona.{CloseHelper, DirectBuffer}
import trumid.poc.impl.server.group.NodeGroup

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.LockSupport

object ClusterConnection {
  val TIMEOUT_SECONDS = 10
  val CONNECT_INTERVAL = 100
  val CONNECT_ATTEMPTS = 10
  val PING_INTERVAL = 1000
  val PING_STEP = 10
  val INGRESS_CHANNEL = "aeron:udp?term-length=512k"
  val EGRESS_CHANNEL = "aeron:udp?term-length=512k|endpoint=%s"
}

class ClusterConnection(listener: ClusterClientHandler, aeron: Aeron, group: NodeGroup, address: String) {

  import ClusterConnection._

  def connect(): ClusterSession = {
    val connect = AeronCluster.asyncConnect(new AeronCluster.Context()
      .aeron(aeron)
      .egressListener(listener)
      .aeronDirectoryName(aeron.context.aeronDirectoryName)
      .ingressChannel(INGRESS_CHANNEL)
      .egressChannel(EGRESS_CHANNEL.format(address))
      .messageTimeoutNs(TimeUnit.SECONDS.toNanos(TIMEOUT_SECONDS))
      .ingressEndpoints(group.getClusterAddresses))

    new AsyncClientSession(listener, connect)
  }

  private class AsyncClientSession(
           private val listener: ClusterClientHandler,
           private var connect: AsyncConnect) extends ClusterSession {

    private val counter: KeepAliveCounter = new KeepAliveCounter(PING_INTERVAL, PING_STEP)
    private var cluster: AeronCluster = null
    private var retry: Long = 0L

    override def offer(buffer: DirectBuffer, offset: Int, length: Int): Long = {
      val cluster: AeronCluster = open()

      if (cluster != null) {
        cluster.offer(buffer, offset, length)
      } else {
        -1
      }
    }

    override def poll(): Int = {
      val cluster: AeronCluster = open()

      if (cluster != null) {
        val count: Int = cluster.pollEgress()

        try {
          counter.send(cluster)
        } catch {
          case cause: Exception =>
            CloseHelper.quietClose(cluster)
            CloseHelper.quietClose(connect)
            listener.onClusterDisconnected(this, cause)
            counter.reset()
            connect = null
        }
        count
      } else {
        0
      }
    }

    override def status(): ClusterStatus = {
      val cluster = open()

      if (connect == null) {
        ClusterDisconnected
      } else if (cluster == null) {
        ClusterConnecting
      } else {
        ClusterConnected
      }
    }

    private def open(): AeronCluster = {
      if (cluster == null && connect != null) {
        val time: Long = System.currentTimeMillis()

        if (retry < time) {
          retry = time + CONNECT_INTERVAL

          try {
            cluster = connect.poll()

            if (cluster != null) {
              var count: Int = 0

              while (count < CONNECT_ATTEMPTS && !counter.send(cluster)) {
                cluster.pollEgress()
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(20))
                count += 1
              }
              cluster.pollEgress()
              listener.onClusterConnected(this)
            }
          } catch {
            case cause: Exception =>
              cause.printStackTrace()
              CloseHelper.quietClose(connect)
              connect = null
          }
        }
      }
      cluster
    }
  }

  class KeepAliveCounter(maxInterval: Long, stepInterval: Long) {

    private var lastTime = 0L
    private var pingTime = 0L
    private var interval = 0L

    def send(cluster: AeronCluster): Boolean = {
      val currentTime = System.currentTimeMillis()

      if (pingTime < currentTime) {
        try {
          if (cluster.sendKeepAlive()) {
            interval = Math.min(interval + stepInterval, maxInterval)
            pingTime = currentTime + interval // only delay ping if successful
            lastTime = currentTime
            true
          } else false
        } catch {
          case cause: Exception =>
            val message = cause.getMessage()
            val duration = currentTime - lastTime
            throw new IllegalStateException(s"Ping failure after $duration ms ($message)", cause)
        }
      } else false
    }

    def reset(): Unit = {
      pingTime = 0
      interval = 0
    }
  }

}