package trumid.poc.impl.server.gateway

import org.agrona.concurrent.Agent
import trumid.poc.common.message.CompletionScheduler
import trumid.poc.common.topic.TopicCompletionPublisher
import trumid.poc.impl.server.client.OutputRingBuffer

import java.util.concurrent.TimeUnit

object ProducerAgent {
  private val SLOW_RESPONSE_TIME = TimeUnit.SECONDS.toMicros(1)
  private val RESPONSE_TIME_SAMPLE_SIZE = 100000
}

class ProducerAgent(consumer: TopicCompletionPublisher, output: OutputRingBuffer, scheduler: CompletionScheduler) extends Agent {
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
    var count = 0

    try {
      count += poller.poll()
      count += scheduler.poll()
    } catch {
      case e: Exception => {
        e.printStackTrace()
        println("Error flushing topics")
      }
    }
    count
  }


  override def roleName(): String = {
    "producer-agent"
  }
}
