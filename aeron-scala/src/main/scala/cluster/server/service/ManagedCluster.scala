package cluster.server.service


import java.util.Collection
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

import io.aeron.{Aeron, DirectBufferVector}
import io.aeron.cluster.service.{ClientSession, Cluster, ClusteredServiceContainer}
import io.aeron.logbuffer.BufferClaim
import org.agrona.DirectBuffer
import org.agrona.concurrent.IdleStrategy

class ManagedCluster(private val cluster: Cluster) extends Cluster {

  override def memberId(): Int = cluster.memberId()

  override def role(): Cluster.Role = cluster.role()

  override def logPosition(): Long = cluster.logPosition()

  override def aeron(): Aeron = cluster.aeron()

  override def context(): ClusteredServiceContainer.Context = cluster.context()

  override def getClientSession(clusterSessionId: Long): ClientSession = cluster.getClientSession(clusterSessionId)

  override def clientSessions(): Collection[ClientSession] = cluster.clientSessions()

  override def forEachClientSession(action: Consumer[_ >: ClientSession]): Unit = cluster.forEachClientSession(action)

  override def closeClientSession(clusterSessionId: Long): Boolean = cluster.closeClientSession(clusterSessionId)

  override def time(): Long = cluster.time()

  override def timeUnit(): TimeUnit = cluster.timeUnit()

  override def scheduleTimer(correlationId: Long, deadline: Long): Boolean = cluster.scheduleTimer(correlationId, deadline)

  override def cancelTimer(correlationId: Long): Boolean = cluster.cancelTimer(correlationId)

  override def offer(buffer: DirectBuffer, offset: Int, length: Int): Long = cluster.offer(buffer, offset, length)

  override def offer(vectors: Array[DirectBufferVector]): Long = cluster.offer(vectors)

  override def tryClaim(length: Int, bufferClaim: BufferClaim): Long = cluster.tryClaim(length, bufferClaim)

  override def idleStrategy(): IdleStrategy = cluster.idleStrategy()

}

