package cluster.server.io

trait Publisher {
  def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean
}

