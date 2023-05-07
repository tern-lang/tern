package cluster.server.client.demo

import cluster.server.TestMode
import cluster.server.client.ClusterClientLauncher
import cluster.server.client.ClusterClientLauncher.ClusterClientConfiguration
import cluster.server.group.NodeGroup

object Main extends App {

  val group = NodeGroup()
  val mode = TestMode
  val client = ClusterClientLauncher.launch(ClusterClientConfiguration(
    handler = new TradingClientAdapter,
    mode = mode,
    group = group,
    host = "localhost:1234"
  ))


}
