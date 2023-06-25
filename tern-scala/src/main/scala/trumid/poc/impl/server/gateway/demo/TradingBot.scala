package trumid.poc.impl.server.gateway.demo

import trumid.poc.example.TradingEngineClient

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class TradingBot(publisher: TradingEngineClient) {

  def execute(count: Int) = {
    for (i: Int <- 1 to count) {
      val call = publisher.placeOrder(
        _.userId(i)
          .accountId(Some(i))
          .time(System.currentTimeMillis())
          .order(
            _.price(11.0)
              .quantity(5555)
              .orderId(s"order${i}")
              .symbol("USD")))

      if(i % 10000 == 0) {
        Thread.`yield`()
      }
      handle(call)
    }
  }

  private def handle(call: Call[PlaceOrderResponse]) = {
    call.call(response => response.time()).onComplete {
      case Success(time) => {
      }
      case Failure(cause) => {
        cause.printStackTrace()
      }
    }(ExecutionContext.global)
  }
}
