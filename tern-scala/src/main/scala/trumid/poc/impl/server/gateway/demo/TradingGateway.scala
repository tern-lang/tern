package trumid.poc.impl.server.gateway.demo

import io.aeron.logbuffer.Header
import org.agrona.DirectBuffer
import trumid.poc.common.message._
import trumid.poc.common.topic._
import trumid.poc.example._
import trumid.poc.impl.server.gateway._

final class TradingGateway extends GatewayHandler {
  private val router: TopicRouter = new TopicRouter(0)
  private val wrapper = new DirectBufferWrapper

  override def onStart(context: GatewayContext): Unit = {
    println("onStart")
    context.getTopicConsumer
      .register(new TradingEngineResponseCodec()
        .complete(context.scheduler))

    router.register(new TradingEngineCodec()
      .topic(new TradingGatewayHandler(router,
        new TradingEnginePublisher(context.getClusterOutput.consume()))))

    router.register(new TradingEngineResponseCodec()
      .topic(context.getGatewayOutput))
    println("onStart FINI")
  }

  override def onContainerMessage(buffer: DirectBuffer, offset: Int, length: Int): Unit = {
    wrapper.wrap(buffer, offset, length)
    router.route(wrapper, 0, length)
  }

  override def onClusterMessage(clusterSessionId: Long, timestamp: Long,
                                buffer: DirectBuffer, offset: Int, length: Int, header: Header): Unit = {
    wrapper.wrap(buffer, offset, length)
    router.route(wrapper, 0, length)
  }
}
