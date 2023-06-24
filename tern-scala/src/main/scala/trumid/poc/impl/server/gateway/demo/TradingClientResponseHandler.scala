package trumid.poc.impl.server.gateway.demo

import trumid.poc.example.TradingEngineResponseHandler
import trumid.poc.example.commands._

import java.util.concurrent.TimeUnit

class TradingClientResponseHandler extends TradingEngineResponseHandler {
  var count = 0

  override def onPlaceOrderResponse(response: PlaceOrderResponse) = {
    val currentTime = TimeUnit.NANOSECONDS.toMicros(System.nanoTime())
    val sendTime = 1
    val duration = currentTime - sendTime
    count += 1
    if(count % 20000 == 0) {
     // println(s"TradingClientResponseHandler.onPlaceOrderResponse duration=${duration} ${response.getOrderId} originalTime=${sendTime} currentTime=${currentTime}")
    }
  }

  override def onCancelOrderResponse(response: CancelOrderResponse) = {

  }

  override def onCancelAllOrdersResponse(response: CancelAllOrdersResponse) = {

  }
}
