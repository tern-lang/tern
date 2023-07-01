package trumid.poc.common.message

import java.lang.{Long => LongMath}
import java.util._
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent._
import scala.concurrent.{Future, Promise}
import scala.reflect.ClassTag

trait Call[T] {
  def call[R](convert: T => R): Future[R]
}

trait Stream[T] {
  def start(consumer: StreamConsumer[T]): Unit
  def flush(): Unit
  def close(): Unit
}

trait StreamConsumer[T] {
  def onUpdate(value: T)
  def onFlush()
  def onClose()
}

trait Completion[T] {
  def complete(value: Any): Completion[T]
  def failure(cause: Throwable): Completion[T]
  def timeout(): Completion[T]
}

class CompletionScheduler {
  private val completions: Map[Long, Completion[_]] = new ConcurrentHashMap[Long, Completion[_]]()
  private val streams: Set[StreamConsumer[_]] = new CopyOnWriteArraySet[StreamConsumer[_]]()
  private val queue: DelayQueue[Completion[_] with Delayed] = new DelayQueue[Completion[_] with Delayed]()
  private val tasks: Queue[Runnable] = new ArrayDeque[Runnable]()
  private val missing: MissingCompletion = new MissingCompletion()

  def call[T: ClassTag](messageId: Byte, correlationId: Long, expiry: Long, trigger: (Completion[T]) => Unit): Call[T] with Completion[T] = {
    val uniqueId = (correlationId << 8) | messageId
    val completion = CallCompletion[T](uniqueId, expiry, trigger, completions)

    println(s"CREATE CALL = ${uniqueId}")
    completions.put(uniqueId, completion)
    queue.offer(completion)
    completion
  }

  def stream[T: ClassTag](messageId: Byte, correlationId: Long, expiry: Long, trigger: (Completion[T]) => Unit): Stream[T] with Completion[T] = {
    val uniqueId = (correlationId << 8) | messageId
    val completion = StreamCompletion[T](uniqueId, expiry, trigger, tasks, streams, completions)

    println(s"CREATE STREAM = ${uniqueId}")
    completions.put(uniqueId, completion)
    queue.offer(completion)
    completion
  }

  def done(messageId: Byte, correlationId: Long): Completion[_] = {
    val uniqueId = (correlationId << 8) | messageId
    val completion: Completion[_] = completions.getOrDefault(uniqueId, missing)

    if (completion.isInstanceOf[Call[_]]) {
      println(s"DONE CALL = ${uniqueId}")
      completions.remove(uniqueId)
    }else if (completion.isInstanceOf[Stream[_]]) {
      println(s"DONE STREAM = ${uniqueId}")
    }else {
      new Exception(s"DONE UNKNOWN = ${uniqueId}").printStackTrace()
    }
    completion
  }

  def poll(): Int = {
    flush()
    execute() + timeout()
  }

  private def flush(): Unit = {
    streams.forEach(_.onFlush())
  }

  private def execute(): Int = {
    var running = true
    var count = 0

    while (running) {
      val next = tasks.poll()

      if (next == null) {
        running = false
      } else {
        next.run()
        count += 1
      }
    }
    count
  }

  private def timeout(): Int = {
    var running = true
    var count = 0

    while (running) {
      val next = queue.poll()

      if (next == null) {
        running = false
      } else {
        next.timeout()
        count += 1
      }
    }
    count
  }

  private class MissingCompletion extends Completion[Any] {

    override def complete(value: Any): Completion[Any] = {
      println("MISSING")
      this
    }

    override def failure(cause: Throwable): Completion[Any] = {
      this
    }

    override def timeout(): Completion[Any] = {
      this
    }
  }

  private case class StreamCompletion[T: ClassTag](correlationId: Long,
                                                   expiry: Long,
                                                   trigger: (Completion[T]) => Unit,
                                                   tasks: Queue[Runnable],
                                                   streams: Set[StreamConsumer[_]],
                                                   completions: Map[Long, Completion[_]])
    extends Completion[T] with Stream[T] with Delayed {

    private val reference: AtomicReference[StreamConsumer[T]] = new AtomicReference[StreamConsumer[T]]()

    override def getDelay(unit: TimeUnit): Long = {
      unit.convert(expiry, TimeUnit.MILLISECONDS)
    }

    override def compareTo(other: Delayed): Int = {
      LongMath.compare(expiry, other.getDelay(TimeUnit.MILLISECONDS))
    }

    override def complete(value: Any): Completion[T] = {
      val consumer = reference.get()

      if(consumer != null) {
        value match {
          case (value: T) => {
            consumer.onUpdate(value)
          }
          case _ =>
        }
      }
      this
    }

    override def failure(cause: Throwable): Completion[T] = {
      val consumer = reference.getAndSet(null)

      if(consumer != null) {
        completions.remove(correlationId)
        streams.remove(consumer)
        tasks.offer(() => consumer.onClose())
      }
      this
    }

    override def timeout(): Completion[T] = {
      val consumer = reference.getAndSet(null)

      if(consumer != null) {
        completions.remove(correlationId)
        streams.remove(consumer)
        tasks.offer(() => consumer.onClose())
      }
      this
    }

    override def flush(): Unit = {
      val consumer = reference.get()

      if(consumer != null) {
        tasks.offer(() => consumer.onFlush())
      }
    }

    override def close(): Unit = {
      val consumer = reference.getAndSet(null)

      if(consumer != null) {
        completions.remove(correlationId)
        streams.remove(consumer)
        tasks.offer(() => consumer.onClose())
      }
    }

    override def start(consumer: StreamConsumer[T]): Unit = {
      if (reference.compareAndSet(null, consumer)) {
        streams.add(consumer)
        trigger.apply(this)
      }
    }
  }

  private case class CallCompletion[T: ClassTag](correlationId: Long,
                                                 expiry: Long,
                                                 trigger: (Completion[T]) => Unit,
                                                 completions: Map[Long, Completion[_]])
    extends Completion[T] with Call[T] with Delayed {

    private val reference: AtomicReference[T => Any] = new AtomicReference[T => Any]()
    private val promise: Promise[Any] = Promise[Any]

    override def getDelay(unit: TimeUnit): Long = {
      unit.convert(expiry, TimeUnit.MILLISECONDS)
    }

    override def compareTo(other: Delayed): Int = {
      LongMath.compare(expiry, other.getDelay(TimeUnit.MILLISECONDS))
    }

    override def complete(value: Any): Completion[T] = {
      val convert = reference.get()

      value match {
        case (value: T) => {
          promise.success(convert.apply(value))
        }
        case _ => {
          promise.failure(new IllegalStateException("Invalid response type"))
        }
      }
      this
    }

    override def failure(cause: Throwable): Completion[T] = {
      completions.remove(correlationId)
      promise.failure(cause)
      this
    }

    override def timeout(): Completion[T] = {
      completions.remove(correlationId)
      promise.failure(new TimeoutException("Request timed out"))
      this
    }

    override def call[R](convert: T => R): Future[R] = {
      if (reference.compareAndSet(null, convert)) {
        trigger.apply(this)
      }
      promise.asInstanceOf[Promise[R]].future
    }
  }
}
