package cluster.server.gateway.demo

import cluster.server.demo.MatchingEngineAdapter
import cluster.server.demo.api.{CancelOrderCommandCodec, CancelOrderResponseCodec, PlaceOrderCommandCodec, PlaceOrderResponseCodec}
import cluster.server.gateway.{GatewayContext, GatewayHandler}
import cluster.server.message.{ByteSize, DirectBufferWrapper, MessageFrame}
import cluster.server.topic.TopicMessageHeader
import io.aeron.cluster.codecs.EventCode
import io.aeron.logbuffer.Header
import org.agrona.DirectBuffer

class TradingClientAdapter extends GatewayHandler {
  private val placeOrder: PlaceOrderCommandCodec = new PlaceOrderCommandCodec
  private val cancelOrder: CancelOrderCommandCodec = new CancelOrderCommandCodec
  private val placeOrderResponse: PlaceOrderResponseCodec = new PlaceOrderResponseCodec
  private val cancelOrderResponse: CancelOrderResponseCodec = new CancelOrderResponseCodec
  private val wrapper = new DirectBufferWrapper
  private var command: TradingClientCommandHandler = _
  private var response: TradingClientResponseHandler = _

  override def onStart(context: GatewayContext): Unit = {
    println("onStart")
    val cluster = context.getClusterOutput
    val client = new TradingClient((frame: MessageFrame, _: Any) => {
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
    val size = startOffset - length

    println("onContainerMessage")
    wrapper.wrap(buffer, offset + startOffset, length)
    buffer.getByte(offset + codeOffset) match {
      case MatchingEngineAdapter.PLACE_ORDER =>
        placeOrderResponse.assign(wrapper, 0, size)
        response.onPlaceOrderResponse(placeOrderResponse)
      case MatchingEngineAdapter.CANCEL_ORDER =>
        cancelOrderResponse.assign(wrapper, 0, size)
        response.onCancelOrderResponse(cancelOrderResponse)
    }
  }

  override def onClusterMessage(clusterSessionId: Long, timestamp: Long,
                                buffer: DirectBuffer, offset: Int, length: Int, header: Header): Unit = {
    val startOffset = TopicMessageHeader.HEADER_SIZE + ByteSize.BYTE_SIZE
    val codeOffset = TopicMessageHeader.HEADER_SIZE
    val size = startOffset - length

    println("onClusterMessage")
    wrapper.wrap(buffer, offset + startOffset, length)
    buffer.getByte(offset + codeOffset) match {
      case MatchingEngineAdapter.PLACE_ORDER_RESPONSE =>
        placeOrderResponse.assign(wrapper, 0, size)
        command.onPlaceOrder(placeOrder)
      case MatchingEngineAdapter.CANCEL_ORDER_RESPONSE =>
        cancelOrderResponse.assign(wrapper, 0, size)
        command.onCancelOrder(cancelOrder)
    }
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
