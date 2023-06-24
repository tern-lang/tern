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

  def apply(member: NodeMember, scheduler: ClusterScheduler, clock: ClusterClock) = {
    val publisher = new ClientSessionPublisher // publish to connected client
    new TradingService(
      new TradingServiceHandler(new TradingServiceOutput(publisher)), publisher, member, scheduler, clock)
  }
}

final class TradingService(handler: TradingEngineHandler,
                           publisher: ClientSessionPublisher,
                           member: NodeMember,
                           scheduler: ClusterScheduler,
                           clock: ClusterClock) extends ManagedService[String] {

  private val router = new TopicRouter(0)
  private val wrapper = new DirectBufferWrapper

  override def onStart(cluster: ManagedCluster, snapshotImage: Image): Unit = {
    clock.start(cluster)
    scheduler.start(cluster)
    publisher.connect(cluster)
    router.register(new TradingEngineCodec().topic(handler))
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
