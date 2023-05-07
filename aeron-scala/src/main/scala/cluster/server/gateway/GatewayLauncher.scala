package cluster.server.gateway

import cluster.server.ClusterMode
import cluster.server.client.ClusterClientLauncher.ClusterClientConfiguration
import cluster.server.client.{ClusterClient, ClusterClientLauncher}
import cluster.server.group.NodeGroup
import cluster.server.topic.TopicMessageConsumer
import org.agrona.concurrent.AgentRunner

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
