package trumid.poc.impl.server.gateway

import trumid.poc.impl.server.client.ClusterClientLauncher.ClusterClientConfiguration
import org.agrona.concurrent.AgentRunner
import trumid.poc.common.topic.TopicMessageConsumer
import trumid.poc.impl.server.ClusterMode
import trumid.poc.impl.server.client.{ClusterClient, ClusterClientLauncher}
import trumid.poc.impl.server.group.NodeGroup

class GatewayLauncher(group: NodeGroup, mode: ClusterMode) {

  def launch(handler: GatewayHandler, consumer: TopicMessageConsumer): ClusterClient = {
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
      client.cluster)))

    AgentRunner.startOnThread(new AgentRunner(
      mode.getIdleStrategy,
      cause => cause.printStackTrace(),
      null,
      new ProducerAgent(consumer, client.output)))

    client
  }
}
