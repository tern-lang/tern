package cluster.server.gateway.demo

import cluster.server.TestMode
import cluster.server.gateway.GatewayLauncher
import cluster.server.group.NodeGroup
import cluster.server.message.MessageFrame
import cluster.server.topic._

object Main extends App {

  class TradingTopicSubscriber extends TopicMessageSubscriber {
    override def onBegin(message: BeginMessage): Unit = {

    }

    override def onResponse(message: ResponseMessage): Unit = {

    }

    override def onFlush(message: FlushMessage): Unit = {

    }
  }

  val mode = TestMode
  val group = NodeGroup()
  val subscriber = new TradingTopicSubscriber
  val consumer = new TopicMessageConsumer(subscriber)
  val gatewayClient = new GatewayLauncher(group, mode).launch(
    handler = new TradingClientAdapter(),
    consumer
  )
  val tradingClient = new TradingClient((frame: MessageFrame, _: Any) => {
    val buffer = frame.getFrame.getBuffer
    val offset = frame.getFrame.getOffset
    val length = frame.getFrame.getLength

    buffer.getBytes(offset, (a, b, c) => gatewayClient.input.publish(a, b, c), length)
  })

  tradingClient.placeOrder(1, 1, 1, 2)
}
