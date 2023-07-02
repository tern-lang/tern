package trumid.poc.impl.server.gateway.demo.http

import org.agrona.collections._
import trumid.poc.common.message._
import trumid.poc.example.events.OrderBookUpdateEvent
import trumid.poc.impl.server.demo.book._

import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.{ConcurrentLinkedQueue, CopyOnWriteArraySet}
import java.util.{Collections, HashMap, Map}

case class OrderBookState(instrumentId: Int, version: Long, bids: Map[Long, Long], offers: Map[Long, Long])
case class OrderBookUpdate(instrumentId: Int, current: OrderBookState, changes: OrderBookState)

class OrderBookModel(instrumentId: Int, scale: PriceScale) {
  private val bids = new Long2ObjectHashMap[MutableLong]()
  private val offers = new Long2ObjectHashMap[MutableLong]()

  def update(side: Side, price: Price, quantity: Long): Unit = {
    val group = if (side.isBuy()) bids else offers
    var value = group.get(price.value)

    if (value == null) {
      value = new MutableLong()
      group.put(price.value, value)
    } else if (value.value + quantity <= 0) {
      group.remove(price.value)
    }
    value.addAndGet(quantity)
  }

  def difference(version: Long, state: OrderBookState): OrderBookUpdate = {
    val newBids = new HashMap[Long, Long]()
    val newOffers = new HashMap[Long, Long]()

    bids.forEach((price, quantity) => newBids.put(price, quantity.value))
    offers.forEach((price, quantity) => newOffers.put(price, quantity.value))

    val updatedBids = new HashMap[Long, Long]()
    val updatedOffers = new HashMap[Long, Long]()

    def update(current: Long2ObjectHashMap[MutableLong], previous: Map[Long, Long], updates: Map[Long, Long]) = {
      current.forEach((price, quantity) => {
        if (!previous.containsKey(price)) {
          updates.put(price, quantity.value)
        }
      })
      previous.forEach((price, quantity) => {
        val newQuantity = current.get(price)

        if (newQuantity != null) {
          if (newQuantity.value != quantity) {
            updates.put(price, newQuantity.value)
          }
        } else {
          updates.put(price, 0)
        }
      })
    }

    update(bids, state.bids, updatedBids)
    update(offers, state.offers, updatedOffers)
    OrderBookUpdate(
      instrumentId,
      OrderBookState(instrumentId, version, newBids, newOffers),
      OrderBookState(instrumentId, version, updatedBids, updatedOffers))
  }
}


object ConflationPublisher {
  val empty = OrderBookState(0, 0, Collections.emptyMap(), Collections.emptyMap())
}

class ConflationPublisher[T](throttle: Long, scale: PriceScale, transformer: OrderBookState => T) extends StreamConsumer[OrderBookUpdateEvent] {
  private val tasks = new ConcurrentLinkedQueue[Runnable]()
  private val listeners = new CopyOnWriteArraySet[T => Unit]()
  private val models = new Int2ObjectHashMap[OrderBookModel]()
  private val states = new Int2ObjectHashMap[OrderBookState]()
  private val counter = new AtomicLong(0)
  private val next = new AtomicLong(0)

  def onJoin(channel: T => Unit): Unit = {
    val version = counter.get()

    tasks.offer(() => {
      try {
        models.forEach((instrumentId, model) => {
          val state = states.get(instrumentId)
          val snapshot = model.difference(version, ConflationPublisher.empty)
          val update = model.difference(version, state)

          channel.apply(transformer.apply(snapshot.changes))

          if (!update.changes.bids.isEmpty || !update.changes.offers.isEmpty) {
            channel.apply(transformer.apply(update.changes))
          }
        })
        listeners.add(channel)
      } catch {
        case cause: Throwable => {
          cause.printStackTrace()
        }
      }
    })
  }

  def onLeave(channel: T => Unit): Unit = {
    tasks.offer(() => listeners.remove(channel))
  }

  override def onUpdate(event: OrderBookUpdateEvent) = {
    val instrumentId = event.instrumentId()
    var model = models.get(instrumentId)

    if (model == null) {
      model = new OrderBookModel(instrumentId, scale)
      states.put(instrumentId, ConflationPublisher.empty)
      models.put(instrumentId, model)
    }
    counter.getAndIncrement()
    event.bids().iterator().foreach(order => model.update(Buy, scale.toPrice(order.price()), order.changeQuantity()))
    event.offers().iterator().foreach(order => model.update(Sell, scale.toPrice(order.price()), order.changeQuantity()))
  }

  override def onFlush() = {
    onDifference()
    onMembership()
  }

  override def onClose() = {
    listeners.forEach(listener => {
      listeners.remove(listener)
    })
  }

  private def onMembership(): Unit = {
    while (!tasks.isEmpty) {
      tasks.poll().run()
    }
  }

  private def onDifference(): Unit = {
    val nextMillis = next.get()
    val timeMillis = System.currentTimeMillis()

    if (throttle == 0 || nextMillis <= timeMillis) {
      next.set(timeMillis + throttle)
      models.forEach((instrumentId, model) => {
        val version = counter.get()
        val previous = states.get(instrumentId)
        val update = model.difference(version, previous)

        states.put(instrumentId, update.current)

        if (!update.changes.bids.isEmpty() || !update.changes.offers.isEmpty()) {
          val message: T = transformer.apply(update.changes)

          listeners.forEach(listener => {
            try {
              listener.apply(message)
            } catch {
              case cause: Throwable => {
                cause.printStackTrace()
                listeners.remove(listener)
              }
            }
          })
        }
      })
    }
  }
}
