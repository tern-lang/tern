package cluster.server.client

import org.agrona.DirectBuffer

sealed trait ClusterStatus {
  def isConnected: Boolean = this eq ClusterConnected
  def isDisconnected: Boolean = this eq ClusterDisconnected
}

object ClusterConnecting extends ClusterStatus
object ClusterConnected extends ClusterStatus
object ClusterDisconnected extends ClusterStatus

trait ClusterSession {
  def status: ClusterStatus
  def offer(buffer: DirectBuffer, offset: Int, length: Int): Long
  def poll: Int
}
