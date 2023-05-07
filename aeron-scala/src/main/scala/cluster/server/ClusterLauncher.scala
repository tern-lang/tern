package cluster.server

import cluster.server.group.NodeMember
import cluster.server.service.ManagedService
import org.agrona.concurrent.EpochClock

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

