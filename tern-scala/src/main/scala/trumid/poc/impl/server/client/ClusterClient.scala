package trumid.poc.impl.server.client

import trumid.poc.common.message.CompletionScheduler
import trumid.poc.common.topic.TopicCompletionPublisher

case class ClusterClient(cluster: ClusterClientPoller,
                         input: InputRingBuffer,
                         output: OutputRingBuffer,
                         scheduler: CompletionScheduler,
                         consumer: TopicCompletionPublisher)
