package trumid.poc.impl.server.gateway.demo

import trumid.poc.impl.server.gateway.GatewayLauncher
import trumid.poc.impl.server.group.NodeGroup
import trumid.poc.impl.server.{ProdMode, TestMode}

object Main {

  def main(args: Array[String]) = {
    val mode = if (args.length == 0) {
      TestMode
    } else {
      args(0).toLowerCase match {
        case "test" => TestMode
        case "prod" => ProdMode
      }
    }
    val throttle = if (args.length > 1) {
      Integer.parseInt(args(1))
    } else {
      2
    }
    val group = NodeGroup()
    val gatewayClient = new GatewayLauncher(group, mode).launch(
      handler = new TradingGateway())

    val tradingBot = new TradingBot(gatewayClient)
    tradingBot.execute(10000)
  }
}
