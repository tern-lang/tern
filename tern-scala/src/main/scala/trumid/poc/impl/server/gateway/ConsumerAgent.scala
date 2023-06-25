package trumid.poc.impl.server.gateway

import org.agrona.concurrent.Agent
import trumid.poc.common.message.CompletionScheduler
import trumid.poc.common.topic.TopicCompletionPublisher
import trumid.poc.impl.server.client.{ClusterClientPoller, OutputRingBuffer}

import java.util.concurrent.atomic.AtomicBoolean

class ConsumerAgent(handler: GatewayHandler,
                    input: InputPoller,
                    cluster: ClusterClientPoller,
                    output: OutputRingBuffer,
                    consumer: TopicCompletionPublisher,
                    scheduler: CompletionScheduler) extends Agent {

  private val started = new AtomicBoolean()

  override def doWork(): Int = {
    var count = 0

    if (cluster.ready) {
      if (started.compareAndSet(false, true)) {
        handler.onStart(GatewayContext(cluster, output, consumer, scheduler))
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
