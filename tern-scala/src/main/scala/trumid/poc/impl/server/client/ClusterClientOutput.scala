package trumid.poc.impl.server.client

import org.agrona.DirectBuffer

trait ClusterClientOutput {
  def publish(buffer: DirectBuffer, offset: Int, length: Int): Boolean
}
