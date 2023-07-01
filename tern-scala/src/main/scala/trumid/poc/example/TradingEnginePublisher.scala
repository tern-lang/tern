// Generated at Sat Jul 01 15:12:09 BST 2023 (ServicePublisher)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._

final class TradingEnginePublisher(consumer: MessageConsumer[TradingEngineCodec]) {
   private val composer = new TopicMessageComposer[TradingEngineCodec](
      new TradingEngineCodec(true),
      DirectByteBuffer(),
      10,
      0)

   def cancelAllOrders(header: MessageHeader, builder: (CancelAllOrdersCommandCodec) => Unit): Unit = {
      val cancelAllOrders = this.composer.compose().cancelAllOrders()

      try {
         builder.apply(cancelAllOrders)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         cancelAllOrders.reset()
      }
   }

   def cancelOrder(header: MessageHeader, builder: (CancelOrderCommandCodec) => Unit): Unit = {
      val cancelOrder = this.composer.compose().cancelOrder()

      try {
         builder.apply(cancelOrder)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         cancelOrder.reset()
      }
   }

   def createInstrument(header: MessageHeader, builder: (CreateInstrumentCommandCodec) => Unit): Unit = {
      val createInstrument = this.composer.compose().createInstrument()

      try {
         builder.apply(createInstrument)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         createInstrument.reset()
      }
   }

   def placeOrder(header: MessageHeader, builder: (PlaceOrderCommandCodec) => Unit): Unit = {
      val placeOrder = this.composer.compose().placeOrder()

      try {
         builder.apply(placeOrder)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         placeOrder.reset()
      }
   }

   def subscribeExecutionReport(header: MessageHeader, builder: (ExecutionReportSubscribeCommandCodec) => Unit): Unit = {
      val subscribeExecutionReport = this.composer.compose().subscribeExecutionReport()

      try {
         builder.apply(subscribeExecutionReport)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         subscribeExecutionReport.reset()
      }
   }

   def subscribeOrderBook(header: MessageHeader, builder: (OrderBookSubscribeCommandCodec) => Unit): Unit = {
      val subscribeOrderBook = this.composer.compose().subscribeOrderBook()

      try {
         builder.apply(subscribeOrderBook)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         subscribeOrderBook.reset()
      }
   }
}
