package trumid.poc.impl.server.client

import org.agrona.BitUtil.findNextPositivePowerOfTwo
import org.agrona.DirectBuffer
import org.agrona.concurrent.ringbuffer.RingBufferDescriptor.TRAILER_LENGTH
import org.agrona.concurrent.ringbuffer.{OneToOneRingBuffer, RingBuffer}
import org.agrona.concurrent.{IdleStrategy, MessageHandler, UnsafeBuffer}
import trumid.poc.common.message._
import trumid.poc.impl.server.ClusterMode

import java.nio.ByteBuffer.allocateDirect

object OutputRingBuffer {
  private val CAPACITY: Int = 256 * 1024 * 1024 // 256 MB

  def create(mode: ClusterMode): OutputRingBuffer = {
    val buffer = new UnsafeBuffer(allocateDirect(findNextPositivePowerOfTwo(CAPACITY) + TRAILER_LENGTH))
    val queue: RingBuffer = new OneToOneRingBuffer(buffer)

    OutputRingBuffer(mode.getIdleStrategy, queue)
  }

  def apply(idleStrategy: IdleStrategy, queue: RingBuffer): OutputRingBuffer =
    new OutputRingBuffer(idleStrategy, queue)
}

case class OutputRingBuffer private(idleStrategy: IdleStrategy, queue: RingBuffer) extends Publisher {

  def consume(handler: MessageHandler, count: Int): Int = {
    queue.read(handler, count)
  }

  def publish(buffer: DirectBuffer, offset: Int, length: Int): Unit = {
    idleStrategy.reset()

    while (!queue.write(1, buffer, offset, length)) {
      idleStrategy.idle()
    }
  }

  override def publish(buffer: ByteBuffer, offset: Int, length: Int): Boolean = {
    buffer.getBytes(offset, (a, b, c) => publish(a, b, c), length)
    true
  }
}