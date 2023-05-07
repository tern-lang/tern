package cluster.server.client

import cluster.server.ClusterMode
import cluster.server.group.NodeGroup

object ClusterClientLauncher {

  case class ClusterClientConfiguration[T](handler: ClusterClientHandler,
                                           mode: ClusterMode,
                                           group: NodeGroup,
                                           host: String)

  def launch[T](configuration: ClusterClientConfiguration[T]): ClusterClient = {
    new ClusterClientMediaDriver(configuration.mode,  configuration.group, configuration.host)
      .launch(configuration.handler)
  }
}
