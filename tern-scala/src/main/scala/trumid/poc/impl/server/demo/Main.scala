package trumid.poc.impl.server.demo

import trumid.poc.impl.server.ClusterLauncher.ClusterConfiguration
import trumid.poc.impl.server.group.NodeMember
import trumid.poc.impl.server.time.{ClusterClock, ClusterScheduler}
import trumid.poc.impl.server.{ClusterLauncher, ProdMode, TestMode}

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
    ClusterLauncher.launch(ClusterConfiguration(
      service = TradingService(
        member = member,
        clock = new ClusterClock,
        scheduler = new ClusterScheduler),
      clock = () => System.currentTimeMillis(),
      mode = mode,
      member = member
    ))
  }
}
