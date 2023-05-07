package cluster.server.gateway

import cluster.server.client.ClusterClientPoller
import org.agrona.concurrent.Agent

class ConsumerAgent(handler: GatewayHandler, input: InputPoller, cluster: ClusterClientPoller) extends Agent {

  override def doWork(): Int = {
    var count = 0

    if (cluster.ready) {
      count += cluster.poll()
      count += input.poll()
    }
    count
  }


  override def roleName(): String = {
    "consumer-agent"
  }
}
