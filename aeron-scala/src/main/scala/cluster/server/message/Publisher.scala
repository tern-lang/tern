package cluster.server.message

trait Publisher {
  def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean
}

