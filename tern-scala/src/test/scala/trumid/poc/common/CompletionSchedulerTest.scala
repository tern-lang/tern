package trumid.poc.common

import trumid.poc.common.message._

import scala.concurrent.ExecutionContext
import scala.util._

object CompletionSchedulerTest extends App {
  val scheduler = new CompletionScheduler()
  val completion1 = scheduler.start[String](1, 10000, _ => {})
  val completion2 = scheduler.start[String](2, 10000, _ => {})

  completion1.call[String](a => a).onComplete {
    case Success(x) => println(x)
    case Failure(t) => t.printStackTrace()
  }(ExecutionContext.global)

  completion2.call[String](a => a).onComplete {
    case Success(x) => println(x)
    case Failure(t) => t.printStackTrace()
  }(ExecutionContext.global)

  scheduler.stop(1).complete("hello")
  scheduler.stop(2).timeout()
}
