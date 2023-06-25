package trumid.poc.impl.server.gateway.demo

import trumid.poc.example.TradingEngineClient

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class TradingBot(publisher: TradingEngineClient) {

  def execute(count: Int) = {
    for (i <- 1 to count) {
      val call = publisher.placeOrder(
        _.userId(i)
          .accountId(Some(i))
          .order(
            _.price(11.0)
              .quantity(11)
              .orderId(s"order${i}")
              .symbol("USD")))


      call.call(response => response.time()).onComplete {
        case Success(time) => println(time)
        case Failure(cause) => cause.printStackTrace()
      }(ExecutionContext.global)
    }
  }
}
