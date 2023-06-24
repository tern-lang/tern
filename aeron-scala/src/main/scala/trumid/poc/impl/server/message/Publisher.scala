package trumid.poc.impl.server.message

trait Publisher {
  def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean
}

