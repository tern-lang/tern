package org.ternlang.tru.common.message

trait Router {
  def route(buffer: ByteBuffer, offset: Int, length: Int): Unit
}
