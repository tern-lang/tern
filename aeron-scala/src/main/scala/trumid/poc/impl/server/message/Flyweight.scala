package trumid.poc.impl.server.message

trait Flyweight[T] {
  def assign(buffer: ByteBuffer, offset: Int, length: Int): T
}
