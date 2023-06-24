package trumid.poc.impl.server.message

import io.aeron.Publication._

import io.aeron.cluster.service.{ClientSession, Cluster}
import org.agrona.DirectBuffer

import java.util.concurrent.TimeUnit

final class ClientSessionPublisher extends Publisher {
  private val clientSessionAdapter: ClientSessionAdapter = new ClientSessionAdapter
  private var cluster: Cluster = _

  def connect(cluster: Cluster): ClientSessionPublisher = {
    this.cluster = cluster
    this
  }

  def withClientSession(clientSession: ClientSession): ClientSessionPublisher = {
    if (cluster == null) {
      throw new IllegalStateException("Cluster must be connected")
    }
    clientSessionAdapter.assign(clientSession)
    this
  }

  override def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean = {
    buffer.getBytes(offset, clientSessionAdapter, length)
    true
  }

  private final class ClientSessionAdapter extends Function3[DirectBuffer, Int, Int, Unit] {
    private var clientSession: ClientSession = _
    private var maximum: Long = _
    private var total: Long = _
    private var count: Int = _

    def assign(clientSession: ClientSession): ClientSessionAdapter = {
      this.clientSession = clientSession
      this
    }

    def apply(buffer: DirectBuffer, offset: Int, length: Int): Unit = {
      val startTime = System.nanoTime()

      while (true) {
        val result = clientSession.offer(buffer, offset, length)

        if (result >= 0) {
          val finishTime = System.nanoTime()
          val duration = TimeUnit.NANOSECONDS.toMicros(finishTime - startTime)

          total += duration
          maximum = Math.max(maximum, duration)

          if (count >= 100000) {
            println(s"Cluster write is ${total / count}/${maximum} micros")
            total = 0
            count = 0
            maximum = 0
          } else {
            count += 1
          }
          return
        } else if (result == MAX_POSITION_EXCEEDED || result == NOT_CONNECTED || result == CLOSED) {
          return
        } else {
          Thread.`yield`()
        }
      }
    }
  }
}
