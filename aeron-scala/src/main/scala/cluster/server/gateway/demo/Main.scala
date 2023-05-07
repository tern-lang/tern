package cluster.server.gateway.demo

import cluster.server.TestMode
import cluster.server.gateway.GatewayLauncher
import cluster.server.group.NodeGroup
import cluster.server.topic.{BeginMessage, FlushMessage, ResponseMessage, TopicMessageConsumer, TopicMessageSubscriber}

object Main extends App {

  class TradingTopicSubscriber extends TopicMessageSubscriber {
    override def onBegin(message: BeginMessage): Unit = {

    }

    override def onResponse(message: ResponseMessage): Unit = {

    }

    override def onFlush(message: FlushMessage): Unit = {

    }
  }

  val group = NodeGroup()
  val mode = TestMode

  val subscriber = new TradingTopicSubscriber
  val consumer = new TopicMessageConsumer(subscriber)
  val client = new TradingClient(new GatewayLauncher(group, mode).launch(
    handler = new TradingClientAdapter(
      command = new TradingClientCommandHandler,
      response = new TradingClientResponseHandler
    ),
    consumer
  ))

  client.placeOrder(1, 1, 1, 2)
}
