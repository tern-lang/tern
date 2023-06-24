package trumid.poc.impl.server.demo

import trumid.poc.impl.server.demo.api.{CancelOrderCommand, PlaceOrderCommand}

import java.util.concurrent.TimeUnit

class MatchingEngine(output: MatchingEngineOutput) {
  var startTime = 0L
  var lastDumpTime = 0L
  var count = 0

  def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    val currentTime = TimeUnit.NANOSECONDS.toMicros(System.nanoTime())
    val sendTime = command.getTime
    val duration = currentTime - sendTime
    if(startTime == 0L) {
      startTime = currentTime
    }
    count += 1
    if(TimeUnit.MICROSECONDS.toSeconds(currentTime - lastDumpTime) > 1) {
      val timeFromStart = TimeUnit.MICROSECONDS.toSeconds(currentTime-startTime).toDouble
      lastDumpTime = currentTime
      println(s"MatchingEngine.onPlaceOrder orderId=${command.getOrderId} count=${count} time=${timeFromStart} ordersPerSecond="+
        (count/timeFromStart) + s" instrumentId=${command.getInstrumentId} duration=${duration}")
    }
    output.onPlaceOrderSuccess(command)
  }

  def onCancelOrder(command: CancelOrderCommand): Unit = {
    //println(s"onCancelOrder.onCancelOrder orderId=${command.getOrderId}")
    output.onCancelOrderSuccess(command)
  }
}
