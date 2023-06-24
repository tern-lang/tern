package trumid.poc.impl.server.gateway.demo

import io.aeron.cluster.codecs.EventCode
import io.aeron.logbuffer.Header
import org.agrona.DirectBuffer
import trumid.poc.common.message._
import trumid.poc.common.topic._
import trumid.poc.example._
import trumid.poc.impl.server.gateway._

class TradingClientAdapter extends GatewayHandler {
  private val commandCodec: TradingEngineCodec  = new TradingEngineCodec()
  private val responseCodec: TradingEngineResponseCodec  = new TradingEngineResponseCodec()
  private val wrapper = new DirectBufferWrapper
  private var command: TradingClientCommandHandler = _
  private var response: TradingClientResponseHandler = _

  override def onStart(context: GatewayContext): Unit = {
    println("onStart")
    val cluster = context.getClusterOutput
    val client = new TradingEngineClient((frame: MessageFrame, _: Any) => {
      val buffer = frame.getFrame.getBuffer
      val offset = frame.getFrame.getOffset
      val length = frame.getFrame.getLength

      buffer.getBytes(offset, (a, b, c) => cluster.publish(a, b, c), length)
    })
    command = new TradingClientCommandHandler(client)
    response = new TradingClientResponseHandler
  }

  override def onContainerMessage(buffer: DirectBuffer, offset: Int, length: Int): Unit = {
    val startOffset = TopicMessageHeader.HEADER_SIZE + ByteSize.BYTE_SIZE
    val codeOffset = TopicMessageHeader.HEADER_SIZE
    val size = length - startOffset

    //println("onContainerMessage")
    wrapper.wrap(buffer, offset + startOffset, size)
    commandCodec.assign(wrapper, 0, size).handle(command)
  }

  override def onClusterMessage(clusterSessionId: Long, timestamp: Long,
                                buffer: DirectBuffer, offset: Int, length: Int, header: Header): Unit = {
    val startOffset = TopicMessageHeader.HEADER_SIZE + ByteSize.BYTE_SIZE
    val codeOffset = TopicMessageHeader.HEADER_SIZE
    val size = length - startOffset

    //println("onClusterMessage")
    wrapper.wrap(buffer, offset + startOffset, size)
    responseCodec.assign(wrapper, 0, size).handle(response)
  }

  override def onClusterSessionEvent(correlationId: Long, clusterSessionId: Long, leadershipTermId: Long,
                                     leaderMemberId: Int, code: EventCode, detail: String): Unit = {
    println("onClusterSessionEvent")
  }

  override def onClusterNewLeader(clusterSessionId: Long, leadershipTermId: Long, leaderMemberId: Int,
                                  memberEndpoints: String): Unit = {
    println("onClusterNewLeader")
  }
}
