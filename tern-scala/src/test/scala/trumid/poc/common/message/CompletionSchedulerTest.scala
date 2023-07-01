package trumid.poc.common.message

import org.scalatest._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class CompletionSchedulerTest extends FlatSpec with Matchers {

  it should "process calls properly" in {
    val scheduler = new CompletionScheduler()
    val completion1 = scheduler.call[String](1,1, 10000, _ => {})
    val completion2 = scheduler.call[String](1, 2, 10000, _ => {})

    completion1.call[String](a => a).onComplete {
      case Success(x) => println(x)
      case Failure(t) => t.printStackTrace()
    }(ExecutionContext.global)

    completion2.call[String](a => a).onComplete {
      case Success(x) => println(x)
      case Failure(t) => t.printStackTrace()
    }(ExecutionContext.global)

    scheduler.done(1, 1).complete("hello")
    scheduler.done(1, 2).timeout()
  }
}
