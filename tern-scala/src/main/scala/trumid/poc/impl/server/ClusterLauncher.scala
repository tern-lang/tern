package trumid.poc.impl.server

import org.agrona.concurrent.EpochClock
import trumid.poc.impl.server.group.NodeMember
import trumid.poc.impl.server.service.ManagedService

object ClusterLauncher {

  case class ClusterConfiguration[T](service: ManagedService[T],
                                     mode: ClusterMode,
                                     member: NodeMember,
                                     clock: EpochClock)

  def launch[T](configuration: ClusterConfiguration[T]): Unit = {
    val node = ClusterNode(
      configuration.service,
      configuration.mode,
      configuration.member)

    node.launch()
  }
}

