package cluster.server.client

import cluster.server.ClusterMode
import io.aeron.protocol.DataHeaderFlyweight
import org.agrona.BitUtil.findNextPositivePowerOfTwo
import org.agrona.DirectBuffer
import org.agrona.concurrent.ringbuffer.{ManyToOneRingBuffer, RingBuffer, RingBufferDescriptor}
import org.agrona.concurrent.{IdleStrategy, MessageHandler, UnsafeBuffer}

import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit


object InputRingBuffer {
  val EXPIRY = TimeUnit.MINUTES.toMillis(1)
  val CAPACITY = 32 * 1024 * 1024 // 32 MB
  val TRAILER_LENGTH = DataHeaderFlyweight.HEADER_LENGTH + RingBufferDescriptor.TRAILER_LENGTH

  def create(mode: ClusterMode): InputRingBuffer = {
    val buffer = new UnsafeBuffer(ByteBuffer.allocateDirect(findNextPositivePowerOfTwo(CAPACITY) + TRAILER_LENGTH))
    val queue = new ManyToOneRingBuffer(buffer)

    InputRingBuffer(mode.getIdleStrategy, queue)
  }
}

case class InputRingBuffer private(idleStrategy: IdleStrategy, queue: RingBuffer) {
  def consume(handler: MessageHandler, count: Int): Int = {
    queue.read(handler, count)
  }

  def publish(buffer: DirectBuffer, offset: Int, length: Int): Unit = {
    var expiry = 0L
    var count = 0

    idleStrategy.reset()

    while (!queue.write(1, buffer, offset, length)) {
      if (count > 100) {
        val time = System.currentTimeMillis()

        if (expiry <= 0) {
          expiry = time + InputRingBuffer.EXPIRY
        } else {
          if (expiry < time) {
            throw new IllegalStateException("Timeout publishing message")
          }
        }
      }
      idleStrategy.idle()
    }
  }
}
