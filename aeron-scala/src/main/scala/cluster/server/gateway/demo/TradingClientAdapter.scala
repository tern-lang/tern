package cluster.server.gateway.demo

import cluster.server.demo.MatchingEngineAdapter
import cluster.server.demo.api.{CancelOrderCommandCodec, PlaceOrderCommandCodec}
import cluster.server.gateway.demo.Main.gatewayClient
import cluster.server.gateway.{GatewayContext, GatewayHandler}
import cluster.server.message.{DirectBufferWrapper, MessageFrame}
import io.aeron.cluster.codecs.EventCode
import io.aeron.logbuffer.Header
import org.agrona.DirectBuffer

class TradingClientAdapter extends GatewayHandler {
  private val placeOrder: PlaceOrderCommandCodec = new PlaceOrderCommandCodec
  private val cancelOrder: CancelOrderCommandCodec = new CancelOrderCommandCodec
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
    println("onContainerMessage")
    wrapper.wrap(buffer, offset, length)
    wrapper.getByte(0) match {
      case MatchingEngineAdapter.PLACE_ORDER =>
        placeOrder.assign(wrapper, 0, length)
        command.onPlaceOrder(placeOrder)
      case MatchingEngineAdapter.CANCEL_ORDER =>
        placeOrder.assign(wrapper, 0, length)
        command.onCancelOrder(cancelOrder)
    }
  }

  override def onClusterMessage(clusterSessionId: Long, timestamp: Long,
                                buffer: DirectBuffer, offset: Int, length: Int, header: Header): Unit = {
    println("onClusterMessage")
    wrapper.wrap(buffer, offset, length)
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
