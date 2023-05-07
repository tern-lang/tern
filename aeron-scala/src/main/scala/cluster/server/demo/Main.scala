package cluster.server.demo

import cluster.server.ClusterLauncher.ClusterConfiguration
import cluster.server.common.ThreadDumper
import cluster.server.group.NodeMember
import cluster.server.time.{ClusterClock, ClusterScheduler}
import cluster.server.{ClusterLauncher, ProdMode, TestMode}

object Main extends App {

  val member = NodeMember()
  val mode = ProdMode
  val dumper = new ThreadDumper()
  //val scheduler = new ScheduledThreadPoolExecutor(1)
  //scheduler.scheduleAtFixedRate(() => println(dumper.dumpThreads()), 1, 1, TimeUnit.SECONDS)

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
