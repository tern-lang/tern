package trumid.poc.impl.server.gateway.demo

import trumid.poc.common.message._
import trumid.poc.common.topic._
import trumid.poc.example._
import trumid.poc.impl.server.gateway.GatewayLauncher
import trumid.poc.impl.server.group.NodeGroup
import trumid.poc.impl.server.{ProdMode, TestMode}

object Main {

  class TradingTopicSubscriber extends TopicMessageSubscriber {
    override def onBegin(message: BeginMessage): Unit = {

    }

    override def onResponse(message: ResponseMessage): Unit = {

    }

    override def onFlush(message: FlushMessage): Unit = {

    }
  }

  def main(args: Array[String]) = {
    val mode = if(args.length == 0) {
      TestMode
    } else {
      args(0).toLowerCase match {
        case "test" => TestMode
        case "prod" => ProdMode
      }
    }
    val throttle = if(args.length > 1) {
      Integer.parseInt(args(1))
    } else {
      2
    }
    val group = NodeGroup()
    val subscriber = new TradingTopicSubscriber
    val consumer = new TopicMessageConsumer(subscriber)
    val gatewayClient = new GatewayLauncher(group, mode).launch(
      handler = new TradingClientAdapter(),
      consumer
    )
    val tradingClient = new TradingEngineClient((frame: MessageFrame, _: Any) => {
      val buffer = frame.getFrame.getBuffer
      val offset = frame.getFrame.getOffset
      val length = frame.getFrame.getLength

      buffer.getBytes(offset, (a, b, c) => gatewayClient.input.publish(a, b, c), length)
    })

    for (i <- 1 to 10000000) {
      tradingClient.publish(i, _.placeOrder()
        .validate())

      if(i % throttle == 0) {
        Thread.sleep(1)
      }
    }
  }
}
