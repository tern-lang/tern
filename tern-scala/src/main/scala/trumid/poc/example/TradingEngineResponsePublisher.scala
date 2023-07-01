// Generated (ServicePublisher)
package trumid.poc.example

import trumid.poc.example.commands._
import trumid.poc.example.events._
import trumid.poc.common.array._
import trumid.poc.common.message._
import trumid.poc.common.topic._

final class TradingEngineResponsePublisher(consumer: MessageConsumer[TradingEngineResponseCodec]) {
   private val composer = new TopicMessageComposer[TradingEngineResponseCodec](
      new TradingEngineResponseCodec(true),
      DirectByteBuffer(),
      11,
      0)

   def cancelAllOrdersResponse(header: MessageHeader, builder: (CancelAllOrdersResponseCodec) => Unit): Unit = {
      val cancelAllOrdersResponse = this.composer.compose().cancelAllOrdersResponse()

      try {
         builder.apply(cancelAllOrdersResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         cancelAllOrdersResponse.reset()
      }
   }

   def cancelOrderResponse(header: MessageHeader, builder: (CancelOrderResponseCodec) => Unit): Unit = {
      val cancelOrderResponse = this.composer.compose().cancelOrderResponse()

      try {
         builder.apply(cancelOrderResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         cancelOrderResponse.reset()
      }
   }

   def createInstrumentResponse(header: MessageHeader, builder: (CreateInstrumentResponseCodec) => Unit): Unit = {
      val createInstrumentResponse = this.composer.compose().createInstrumentResponse()

      try {
         builder.apply(createInstrumentResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         createInstrumentResponse.reset()
      }
   }

   def placeOrderResponse(header: MessageHeader, builder: (PlaceOrderResponseCodec) => Unit): Unit = {
      val placeOrderResponse = this.composer.compose().placeOrderResponse()

      try {
         builder.apply(placeOrderResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         placeOrderResponse.reset()
      }
   }

   def subscribeExecutionReportResponse(header: MessageHeader, builder: (ExecutionReportEventCodec) => Unit): Unit = {
      val subscribeExecutionReportResponse = this.composer.compose().subscribeExecutionReportResponse()

      try {
         builder.apply(subscribeExecutionReportResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         subscribeExecutionReportResponse.reset()
      }
   }

   def subscribeOrderBookResponse(header: MessageHeader, builder: (OrderBookUpdateEventCodec) => Unit): Unit = {
      val subscribeOrderBookResponse = this.composer.compose().subscribeOrderBookResponse()

      try {
         builder.apply(subscribeOrderBookResponse)
         this.composer.commit(this.consumer, header.getUserId, header.getCorrelationId)
      } finally {
         subscribeOrderBookResponse.reset()
      }
   }
}
