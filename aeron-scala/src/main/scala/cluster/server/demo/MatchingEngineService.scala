package cluster.server.demo

import cluster.server.group.NodeMember
import cluster.server.message.{ClientSessionPublisher, DirectBufferWrapper}
import cluster.server.service.{ManagedCluster, ManagedService}
import cluster.server.time.{ClusterClock, ClusterScheduler}
import cluster.server.topic.TopicRouter
import io.aeron.cluster.codecs.CloseReason
import io.aeron.cluster.service.{ClientSession, Cluster}
import io.aeron.logbuffer.Header
import io.aeron.{ExclusivePublication, Image}
import org.agrona.DirectBuffer

object MatchingEngineService {

  def apply(member: NodeMember,scheduler: ClusterScheduler, clock: ClusterClock) = {
    val me: MatchingEngine = new  MatchingEngine
    new MatchingEngineService(me, member, scheduler, clock)
  }
}

class MatchingEngineService(me: MatchingEngine, member: NodeMember, scheduler: ClusterScheduler, clock: ClusterClock) extends ManagedService[String] {
  val publisher = new ClientSessionPublisher // publish to connected client
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
