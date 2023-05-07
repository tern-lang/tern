package cluster.server.message

trait Router {
  def route(buffer: ByteBuffer, offset: Int, length: Int): Unit
}
