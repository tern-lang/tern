package trumid.poc.impl.server.client

import io.aeron.Publication.{CLOSED, MAX_POSITION_EXCEEDED, NOT_CONNECTED}
import org.agrona.DirectBuffer
import org.agrona.collections.MutableReference

class ClusterClientPoller(client: ClusterConnection, idle: Runnable) extends ClusterClientOutput {

  private val reference = new MutableReference[ClusterSession]

  def session(): ClusterSession = reference.get()

  def ready(): Boolean = {
    val connection = connect()
    val status = connection.status()

    status.isConnected
  }

  def publish(buffer: DirectBuffer, offset: Int, length: Int): Boolean = {
    val connection = connect()
    val status = connection.status()

    if (status.isConnected) {
      while (true) {
        val result = connection.offer(buffer, offset, length)

        if (result >= 0) {
          return true
        }
        if (result == MAX_POSITION_EXCEEDED) {
          return false
        }
        if (result == NOT_CONNECTED) {
          return false
        }
        if (result == CLOSED) {
          return false
        }
        idle.run()
      }
    }
    false
  }

  def poll(): Int = {
    val connection = connect()
    var count = 0

    if (connection.status().isConnected) {
      count += connection.poll()
    }
    count
  }

  private def connect(): ClusterSession = {
    val connection: ClusterSession = reference.get()

    if (connection == null) {
      reference.set(client.connect())
    } else {
      val status = connection.status()

      if (status.isDisconnected) {
        try {
          reference.set(client.connect())
        } catch {
          case cause: RuntimeException =>
            cause.printStackTrace()
            Thread.sleep(100)
            throw cause
        }
      }
    }
    reference.get()
  }
}

