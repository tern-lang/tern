package trumid.poc.impl.server.gateway.demo

import trumid.poc.example.TradingEngineClient

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class TradingBot(publisher: TradingEngineClient) {

  def execute(count: Int) = {
    for (i <- 1 to count) {
      val call = publisher.placeOrder(x => {
        x.userId(i)
          .accountId(Some(i))
          .order(
            _.price(11.0)
              .quantity(5555)
              .orderId(s"order${i}")
              .symbol("USD"))

        println(x.order().orderId())
      })


      call.call(response => response.time()).onComplete {
        case Success(time) => println(time)
        case Failure(cause) => cause.printStackTrace()
      }(ExecutionContext.global)
    }
  }
}
