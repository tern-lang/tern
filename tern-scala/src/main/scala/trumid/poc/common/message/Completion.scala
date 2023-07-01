package trumid.poc.common.message

import java.lang.{Long => LongMath}
import java.util.Map
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.{ConcurrentHashMap, DelayQueue, Delayed, TimeUnit}
import scala.concurrent._
import scala.reflect.ClassTag

trait Call[T] {
  def call[R](convert: T => R): Future[R]
}

trait Stream[T] {
  def start(update: (T) => Unit): Unit
}

trait Completion[T] {
  def complete(value: Any): Completion[T]
  def failure(cause: Throwable): Completion[T]
  def timeout(): Completion[T]
  def transient(): Boolean
}

class CompletionScheduler {
  private val completions: Map[Long, Completion[_]] = new ConcurrentHashMap[Long, Completion[_]]()
  private val queue: DelayQueue[Completion[_] with Delayed] = new DelayQueue[Completion[_] with Delayed]()
  private val missing: MissingCompletion = new MissingCompletion()

  def call[T: ClassTag](correlationId: Long, expiry: Long, trigger: (Completion[T]) => Unit): Call[T] with Completion[T] = {
    val completion = CallCompletion[T](correlationId, expiry, trigger, completions)

    completions.put(correlationId, completion)
    queue.offer(completion)
    completion
  }

  def stream[T: ClassTag](correlationId: Long, expiry: Long, trigger: (Completion[T]) => Unit): Stream[T] with Completion[T] = {
    val completion = StreamCompletion[T](correlationId, expiry, trigger, completions)

    completions.put(correlationId, completion)
    queue.offer(completion)
    completion
  }

  def done(correlationId: Long): Completion[_] = {
    val completion: Completion[_] = completions.getOrDefault(correlationId, missing)

    if (completion.transient()) {
      completions.remove(correlationId)
    }
    completion
  }

  def poll(): Int = {
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
      this
    }

    override def failure(cause: Throwable): Completion[Any] = {
      this
    }

    override def timeout(): Completion[Any] = {
      this
    }

    override def transient(): Boolean = {
      true
    }
  }

  private case class StreamCompletion[T: ClassTag](correlationId: Long,
                                                   expiry: Long,
                                                   trigger: (Completion[T]) => Unit,
                                                   completions: Map[Long, Completion[_]])
    extends Completion[T] with Stream[T] with Delayed {

    private val reference: AtomicReference[T => Unit] = new AtomicReference[T => Unit]()

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
          convert.apply(value)
        }
        case _ =>
      }
      this
    }

    override def failure(cause: Throwable): Completion[T] = {
      completions.remove(correlationId)
      this
    }

    override def timeout(): Completion[T] = {
      completions.remove(correlationId)
      this
    }

    override def transient(): Boolean = {
      false
    }

    override def start(update: (T) => Unit): Unit = {
      if (reference.compareAndSet(null, update)) {
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

    override def transient(): Boolean = {
      true
    }

    override def call[R](convert: T => R): Future[R] = {
      if (reference.compareAndSet(null, convert)) {
        trigger.apply(this)
      }
      promise.asInstanceOf[Promise[R]].future
    }
  }
}
