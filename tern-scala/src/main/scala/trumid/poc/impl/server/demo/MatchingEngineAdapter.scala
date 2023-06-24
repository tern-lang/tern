package trumid.poc.impl.server.demo

import trumid.poc.common.message._
import trumid.poc.common.topic._
import trumid.poc.example.commands._

object MatchingEngineAdapter {
  val PLACE_ORDER: Byte = 1
  val CANCEL_ORDER: Byte = 2
  val PLACE_ORDER_RESPONSE: Byte = 2
  val CANCEL_ORDER_RESPONSE: Byte = 2
}

class MatchingEngineAdapter(me: MatchingEngine) extends TopicRoute {
  private val placeOrder: PlaceOrderCommandCodec = new PlaceOrderCommandCodec
  private val cancelOrder: CancelOrderCommandCodec = new CancelOrderCommandCodec

  override def handle(frame: MessageFrame, payload: Fragment): Unit = {
    payload.getBuffer.getByte(payload.getOffset) match {
      case MatchingEngineAdapter.PLACE_ORDER =>
        placeOrder.assign(payload.getBuffer, payload.getOffset + ByteSize.BYTE_SIZE, payload.getLength - ByteSize.BYTE_SIZE)
        me.onPlaceOrder(placeOrder)
      case MatchingEngineAdapter.CANCEL_ORDER =>
        cancelOrder.assign(payload.getBuffer, payload.getOffset + ByteSize.BYTE_SIZE, payload.getLength - ByteSize.BYTE_SIZE)
        me.onCancelOrder(cancelOrder)
    }
  }

  override def getTopic: Topic = {
    Topic.MatchingEngine
  }
}
