package cluster.server.demo

import cluster.server.ClusterLauncher.ClusterConfiguration
import cluster.server.group.NodeMember
import cluster.server.time.{ClusterClock, ClusterScheduler}
import cluster.server.{ClusterLauncher, TestMode}

object Main extends App {

  val member = NodeMember()
  val mode = TestMode

  ClusterLauncher.launch(ClusterConfiguration(
    service = MatchingEngineService(
      member = member,
      clock = new ClusterClock,
      scheduler = new ClusterScheduler),
    clock = () => System.currentTimeMillis(),
    mode = mode,
    member = member
  ))
}
