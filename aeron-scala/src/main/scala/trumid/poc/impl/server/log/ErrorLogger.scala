package trumid.poc.impl.server.log

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.LockSupport

class ErrorLogger(source: String, fatal: Boolean) extends org.agrona.ErrorHandler {

  override def onError (cause: Throwable) = {
    println(s"Error occurred for '$source'")

    if (fatal) try {
      cause.printStackTrace()
      LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5))
    } finally System.exit(1)
  }
}
