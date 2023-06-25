package trumid.poc.impl.server.gateway.demo

import trumid.poc.common.message.MessageFrame
import trumid.poc.example.TradingEngineClient
import trumid.poc.impl.server.client._

class TradingBot(client: ClusterClient) {
  private val publisher = new TradingEngineClient((frame: MessageFrame, _: Any) =>
    frame.getFrame.getBuffer.getBytes(
      frame.getFrame.getOffset,
      (buffer, offset, length) => client.input.publish(buffer, offset, length),
      frame.getFrame.getLength), client.scheduler)

  def execute(count: Int) = {
    for (i <- 1 to count) {
      publisher.placeOrder(
        _.userId(i)
          .accountId(Some(i))
          .order(
            _.price(11.0)
              .quantity(11)
              .orderId(s"order${i}")
              .symbol("USD")))
    }
  }
}
