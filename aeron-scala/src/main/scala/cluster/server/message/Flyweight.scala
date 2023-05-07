package cluster.server.message

trait Flyweight[T] {
  def assign(buffer: ByteBuffer, offset: Int, length: Int): T
}
