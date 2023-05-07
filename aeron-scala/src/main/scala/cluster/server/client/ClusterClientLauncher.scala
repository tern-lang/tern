package cluster.server.client

import cluster.server.ClusterMode
import cluster.server.group.NodeGroup
import org.agrona.concurrent.EpochClock

object ClusterClientLauncher {

  case class ClusterClientConfiguration[T](handler: ClusterClientHandler,
                                           mode: ClusterMode,
                                           group: NodeGroup,
                                           host: String)

  def launch[T](configuration: ClusterClientConfiguration[T]): Unit = {
    new ClusterClientMediaDriver(configuration.mode,  configuration.group, configuration.host)
      .launch(configuration.handler)
  }
}
