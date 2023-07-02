package trumid.poc.impl.server.gateway.demo.http

import trumid.poc.common.message.StreamConsumer
import trumid.poc.example.events.OrderBookUpdateEvent
import trumid.poc.impl.server.demo.book.PriceScale

import java.util.{Iterator, Map}

class JsonPublisher(throttle: Long) extends StreamConsumer[OrderBookUpdateEvent] {
  private val writer = new JsonWriter()
  private val publisher = new ConflationPublisher[String](throttle, PriceScale.DEFAULT, writer.write)

  def onJoin(update: String => Unit) = {
    publisher.onJoin(update)
  }

  override def onUpdate(event: OrderBookUpdateEvent) = {
    publisher.onUpdate(event)
  }

  override def onFlush() = {
    publisher.onFlush()
  }

  override def onClose() = {
    publisher.onClose()
  }
}

class JsonWriter {
  private val builder = new StringBuilder()

  def write(state: OrderBookState): String = {
    builder.setLength(0)
    builder.append("{")
      .append('"')
      .append("id")
      .append('"')
      .append(":")
      .append(state.instrumentId)
      .append(",")
      .append('"')
      .append("version")
      .append('"')
      .append(":")
      .append(state.version)
      .append(",")

    write("bids", state.bids.entrySet().iterator())
    builder.append(",")
    write("offers", state.offers.entrySet().iterator())
    builder.append("}")
    builder.toString()
  }

  private def write(name: String, values: Iterator[Map.Entry[Long, Long]]): Unit = {
    var index = 0

    builder.append('"')
      .append(name)
      .append('"')
      .append(":[")

    while (values.hasNext) {
      val entry = values.next()

      index += 1
      builder.append(if (index > 1) "," else "")
        .append("{")
        .append('"')
        .append("px")
        .append('"')
        .append(":")
        .append(entry.getKey)
        .append(",")
        .append('"')
        .append("qty")
        .append('"')
        .append(":")
        .append(entry.getValue)
        .append("}")
    }
    builder.append("]")
  }
}