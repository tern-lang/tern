package cluster.server.demo

import cluster.server.ClusterLauncher.ClusterConfiguration
import cluster.server.common.ThreadDumper
import cluster.server.group.NodeMember
import cluster.server.time.{ClusterClock, ClusterScheduler}
import cluster.server.{ClusterLauncher, ProdMode, TestMode}

object Main {

  def main(args: Array[String]) = {
    val mode = if(args.length == 0) {
      TestMode
    } else {
      args(0).toLowerCase match {
        case "test" => TestMode
        case "prod" => ProdMode
      }
    }
    val member = NodeMember()
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
}
