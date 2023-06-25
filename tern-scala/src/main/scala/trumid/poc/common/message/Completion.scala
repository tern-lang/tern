package trumid.poc.common.message

import java.util.concurrent.{ConcurrentHashMap, DelayQueue, Delayed, TimeUnit}
import scala.concurrent._
import java.util.Map
import java.lang.{Long => LongMath}
import scala.reflect.ClassTag

trait Completion {
  def complete(value: Any): Completion
  def failure(cause: Throwable): Completion
  def timeout(): Completion
  def future[T: ClassTag](): Future[T]
}

class CompletionScheduler {
  private val completions: Map[Long, Completion] = new ConcurrentHashMap[Long, Completion]()
  private val queue: DelayQueue[PromiseCompletion] = new DelayQueue[PromiseCompletion]()

  def start(correlationId: Long, expiry: Long): Completion = {
    val completion = PromiseCompletion(correlationId, expiry, completions)

    queue.offer(completion)
    completions.computeIfAbsent(correlationId, ignore => completion)
    completion
  }

  def stop(correlationId: Long): Completion = {
    val completion: Completion = completions.remove(correlationId)

    if(completion == null) {
      MissingCompletion(correlationId, completions)
    } else {
      completion
    }
  }

  def poll(): Int = {
    val count = queue.size()

    while(queue.isEmpty) {
      queue.poll().timeout()
    }
    count
  }

  private case class MissingCompletion(correlationId: Long,
                                          completions: Map[Long, Completion])
        extends Completion {

    private val promise: Promise[Any] = Promise[Any]

    override def complete(value: Any): Completion = {
      completions.remove(correlationId)
      this
    }

    override def failure(cause: Throwable): Completion = {
      completions.remove(correlationId)
      this
    }

    override def timeout(): Completion = {
      completions.remove(correlationId)
      this
    }

    override def future[T: ClassTag](): Future[T] = {
      promise.asInstanceOf[Promise[T]].future
    }
  }

  private case class PromiseCompletion(correlationId: Long,
                                          expiry: Long,
                                          completions: Map[Long, Completion])
        extends Completion with Delayed {

    private val promise: Promise[Any] = Promise[Any]

    override def getDelay(unit: TimeUnit): Long =  {
      unit.convert(expiry, TimeUnit.MILLISECONDS)
    }

    override def compareTo(other: Delayed): Int = {
      LongMath.compare(expiry, other.getDelay(TimeUnit.MILLISECONDS))
    }

    override def complete(value: Any): Completion = {
      val completion = completions.remove(correlationId)

      if(completion != null) {
        promise.success(value)
      }
      this
    }

    override def failure(cause: Throwable): Completion = {
      val completion = completions.remove(correlationId)

      if(completion != null) {
        promise.failure(cause)
      }
      this
    }

    override def timeout(): Completion = {
      val completion = completions.remove(correlationId)

      if(completion != null) {
        promise.failure(new TimeoutException())
      }
      this
    }

    override def future[T: ClassTag](): Future[T] = {
      promise.asInstanceOf[Promise[T]].future
    }
  }
}
