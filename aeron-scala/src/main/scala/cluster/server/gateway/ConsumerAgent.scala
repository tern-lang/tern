package cluster.server.gateway

import cluster.server.client.ClusterClientPoller
import org.agrona.concurrent.Agent

import java.util.concurrent.atomic.AtomicBoolean

class ConsumerAgent(handler: GatewayHandler, input: InputPoller, cluster: ClusterClientPoller) extends Agent {
  private val started = new AtomicBoolean()

  override def doWork(): Int = {
    var count = 0

    if (cluster.ready) {
      if(started.compareAndSet(false, true)) {
        handler.onStart(GatewayContext(cluster))
      }
      count += cluster.poll()
      count += input.poll()
    }
    count
  }


  override def roleName(): String = {
    "consumer-agent"
  }
}
