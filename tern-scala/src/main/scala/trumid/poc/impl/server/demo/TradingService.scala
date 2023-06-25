package trumid.poc.impl.server.demo

import io.aeron.Image
import io.aeron.cluster.service.ClientSession
import io.aeron.logbuffer.Header
import org.agrona.DirectBuffer
import trumid.poc.common.message._
import trumid.poc.common.topic.TopicRouter
import trumid.poc.example.{TradingEngineCodec, TradingEngineHandler}
import trumid.poc.impl.server.group.NodeMember
import trumid.poc.impl.server.service.{ManagedCluster, ManagedService}
import trumid.poc.impl.server.time.{ClusterClock, ClusterScheduler}

object TradingService {

  def apply(member: NodeMember, scheduler: ClusterScheduler, clock: ClusterClock): TradingService = {
    val router = new TopicRouter(0)
    val publisher = new ClientSessionPublisher // publish to connected client
    val output = new TradingServiceOutput(router, publisher)
    val handler = new TradingServiceHandler(output)

    new TradingService(handler, publisher, router, member, scheduler, clock)
  }
}

final class TradingService(handler: TradingEngineHandler,
                           publisher: ClientSessionPublisher,
                           router: TopicRouter,
                           member: NodeMember,
                           scheduler: ClusterScheduler,
                           clock: ClusterClock) extends ManagedService[String] {

  private val wrapper = new DirectBufferWrapper
  private val codec = new TradingEngineCodec()

  override def onStart(cluster: ManagedCluster, snapshotImage: Image): Unit = {
    clock.start(cluster)
    scheduler.start(cluster)
    publisher.connect(cluster)
    router.register(codec.topic(handler))
  }

  override def onSessionMessage(clientSession: ClientSession,
                                l: Long,
                                directBuffer: DirectBuffer,
                                offset: Int,
                                length: Int,
                                header: Header): Unit = {
    publisher.withClientSession(clientSession)
    wrapper.wrap(directBuffer, offset, length)
    router.route(wrapper, 0, length)
  }
}
