package org.ternlang.tru.common.message

class Fragment {

  private var buffer: ByteBuffer = _
  private var offset: Int = _
  private var length: Int = _
  private var version: Int = _

  def assign(buffer: ByteBuffer, offset: Int, length: Int): Fragment = {
    this.buffer = buffer
    this.offset = offset
    this.length = length
    this
  }

  def assign(buffer: ByteBuffer, offset: Int, length: Int, version: Int): Fragment = {
    this.buffer = buffer
    this.offset = offset
    this.length = length
    this.version = version
    this
  }

  def getVersion: Int = version

  def getBuffer: ByteBuffer = buffer

  def getOffset: Int = offset

  def getLength: Int = length
}
