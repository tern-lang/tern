package trumid.poc.impl.server.gateway

import trumid.poc.common.message.CompletionScheduler
import trumid.poc.common.topic.TopicCompletionPublisher
import trumid.poc.impl.server.client.{ClusterClientOutput, OutputRingBuffer}

case class GatewayContext(cluster: ClusterClientOutput,
                          output: OutputRingBuffer,
                          consumer: TopicCompletionPublisher,
                          scheduler: CompletionScheduler) {
  def getClusterOutput: ClusterClientOutput = cluster
  def getGatewayOutput: OutputRingBuffer = output
  def getTopicConsumer: TopicCompletionPublisher = consumer
  def getScheduler: CompletionScheduler = scheduler
}
