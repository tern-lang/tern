package trumid.poc.impl.server.gateway

import org.agrona.concurrent.Agent
import trumid.poc.impl.server.client.OutputRingBuffer
import trumid.poc.impl.server.topic.TopicMessageConsumer

import java.util.concurrent.TimeUnit

object ProducerAgent {
  private val SLOW_RESPONSE_TIME = TimeUnit.SECONDS.toMicros(1)
  private val RESPONSE_TIME_SAMPLE_SIZE = 100000
}

class ProducerAgent(consumer: TopicMessageConsumer, output: OutputRingBuffer) extends Agent {
  private val poller = new OutputPoller(output, consumer)
  private var maximum = 0L
  private var count = 0L
  private var total = 0L

  def doWork(): Int = {
    val start = System.nanoTime()

    try {
      process()
    } finally {
      val finish = System.nanoTime()
      val duration = TimeUnit.NANOSECONDS.toMicros(finish - start)

      if (duration > ProducerAgent.SLOW_RESPONSE_TIME) {
        println(s"Slow response of $duration micros")
      }
      maximum = if (duration > maximum) duration else maximum
      total += duration

      if (count >= ProducerAgent.RESPONSE_TIME_SAMPLE_SIZE) {
        println(s"Response time is ${total / count}/${maximum} micros")
        maximum = duration
        total = duration
        count = 1
      } else {
        count += 1
      }
    }
  }

  private def process(): Int = {
    val count = poller.poll()
    try {
      consumer.flush()
      count
    } catch {
      case e: Exception => println("Error flushing topics");
        count
    }
  }


  override def roleName(): String = {
    "producer-agent"
  }
}
