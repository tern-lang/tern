package trumid.poc.common.message

trait Router {
  def route(buffer: ByteBuffer, offset: Int, length: Int): Unit
}
