package trumid.poc.common.message

import io.aeron.cluster.service.Cluster

class BroadcastPublisher extends Publisher {
  private val publisher: ClientSessionPublisher = new ClientSessionPublisher
  private var cluster: Cluster = _

  def connect(cluster: Cluster): BroadcastPublisher = {
    this.cluster = cluster
    this.publisher.connect(cluster)
    this
  }

  override def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean = {
    cluster.clientSessions().forEach(session => {
      publisher.withClientSession(session).publish(buffer, offset, length)
    })
    true
  }

}
