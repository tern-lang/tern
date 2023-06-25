package trumid.poc.impl.server.gateway

import org.agrona.concurrent.AgentRunner
import trumid.poc.impl.server.ClusterMode
import trumid.poc.impl.server.client.ClusterClientLauncher.ClusterClientConfiguration
import trumid.poc.impl.server.client.{ClusterClient, ClusterClientLauncher}
import trumid.poc.impl.server.group.NodeGroup

class GatewayLauncher(group: NodeGroup, mode: ClusterMode) {

  def launch(handler: GatewayHandler): ClusterClient = {
    val client = ClusterClientLauncher.launch(ClusterClientConfiguration(
      handler,
      mode = mode,
      group = group,
      host = "localhost:1234"
    ))

    AgentRunner.startOnThread(new AgentRunner(
      mode.getIdleStrategy,
      cause => cause.printStackTrace(),
      null,
      new ConsumerAgent(handler,
        new InputPoller(client.input, handler),
        client.cluster,
        client.output,
        client.consumer,
        client.scheduler)))

    AgentRunner.startOnThread(new AgentRunner(
      mode.getIdleStrategy,
      cause => cause.printStackTrace(),
      null,
      new ProducerAgent(client.consumer, client.output, client.scheduler)))

    client
  }
}
