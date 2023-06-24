package trumid.poc.impl.server.demo

import trumid.poc.example.TradingEngineHandler
import trumid.poc.example.commands._

import java.util.concurrent.TimeUnit

class MatchingEngine(output: MatchingEngineOutput) extends TradingEngineHandler {
  var startTime = 0L
  var lastDumpTime = 0L
  var count = 0

  override def onPlaceOrder(command: PlaceOrderCommand): Unit = {
    val currentTime = TimeUnit.NANOSECONDS.toMicros(System.nanoTime())
    val sendTime = 1
    val duration = currentTime - sendTime
    if(startTime == 0L) {
      startTime = currentTime
    }
    count += 1
    if(TimeUnit.MICROSECONDS.toSeconds(currentTime - lastDumpTime) > 1) {
      val timeFromStart = TimeUnit.MICROSECONDS.toSeconds(currentTime-startTime).toDouble
      lastDumpTime = currentTime
//      println(s"MatchingEngine.onPlaceOrder orderId=${command.getOrderId} count=${count} time=${timeFromStart} ordersPerSecond="+
//        (count/timeFromStart) + s" instrumentId=${command.getInstrumentId} duration=${duration}")
    }
    output.onPlaceOrderSuccess(command)
  }

  override def onCancelOrder(command: CancelOrderCommand): Unit = {
    //println(s"onCancelOrder.onCancelOrder orderId=${command.getOrderId}")
    output.onCancelOrderSuccess(command)
  }

  override def onCancelAllOrders(command: CancelAllOrdersCommand): Unit = {
    //println(s"onCancelOrder.onCancelOrder orderId=${command.getOrderId}")
   // output.onCancelOrderSuccess(command)
  }
}
