package trumid.poc.impl.server.demo

import io.aeron.cluster.codecs.CloseReason
import io.aeron.cluster.service.{ClientSession, Cluster}
import io.aeron.logbuffer.Header
import io.aeron.{ExclusivePublication, Image}
import org.agrona.DirectBuffer
import trumid.poc.impl.server.group.NodeMember
import trumid.poc.impl.server.message.{ClientSessionPublisher, DirectBufferWrapper}
import trumid.poc.impl.server.service.{ManagedCluster, ManagedService}
import trumid.poc.impl.server.time.{ClusterClock, ClusterScheduler}
import trumid.poc.impl.server.topic.TopicRouter

object MatchingEngineService {

  def apply(member: NodeMember, scheduler: ClusterScheduler, clock: ClusterClock) = {
    val publisher = new ClientSessionPublisher // publish to connected client
    val me: MatchingEngine = new MatchingEngine(new MatchingEngineOutput(publisher))
    new MatchingEngineService(me, publisher, member, scheduler, clock)
  }
}

class MatchingEngineService(me: MatchingEngine, publisher: ClientSessionPublisher, member: NodeMember, scheduler: ClusterScheduler, clock: ClusterClock) extends ManagedService[String] {
  val router = new TopicRouter(0)
  val wrapper = new DirectBufferWrapper
  val adapter = new MatchingEngineAdapter(me)

  override def onStart(cluster: ManagedCluster, snapshotImage: Image): Unit = {
    clock.start(cluster)
    scheduler.start(cluster)
    publisher.connect(cluster)
    router.register(adapter)
  }

  override def onReady(cluster: ManagedCluster): Unit = {

  }

  override def onState(consumer: String => Unit): Unit = {

  }

  override def onSessionOpen(clientSession: ClientSession, l: Long): Unit = {

  }

  override def onSessionClose(clientSession: ClientSession, l: Long, closeReason: CloseReason): Unit = {

  }

  override def onSessionMessage(clientSession: ClientSession, l: Long, directBuffer: DirectBuffer, offset: Int, length: Int, header: Header): Unit = {
    publisher.withClientSession(clientSession)
    wrapper.wrap(directBuffer, offset, length)
    router.route(wrapper, 0, length)
  }

  override def onTimerEvent(l: Long, l1: Long): Unit = {

  }

  override def onTakeSnapshot(exclusivePublication: ExclusivePublication): Unit = {

  }

  override def onRoleChange(role: Cluster.Role): Unit = {

  }

  override def onTerminate(cluster: Cluster): Unit = {

  }
}
