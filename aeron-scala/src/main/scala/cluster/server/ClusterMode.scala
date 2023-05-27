package cluster.server

import io.aeron.archive.ArchiveThreadingMode
import io.aeron.driver.ThreadingMode
import org.agrona.concurrent.{BackoffIdleStrategy, IdleStrategy, YieldingIdleStrategy}

import java.util.concurrent.TimeUnit.{MICROSECONDS, MILLISECONDS}

object TestMode extends ClusterMode
object ProdMode extends ClusterMode

sealed trait ClusterMode {

  def isTest: Boolean = this eq TestMode

  def isProd: Boolean = this eq ProdMode

  // XXX delete on start for PROD ONLY
  def isDeleteOnStart: Boolean = isTest || isProd

  def getThreadingMode: ThreadingMode = {
    if (isProd) {
      ThreadingMode.DEDICATED
    } else {
      ThreadingMode.SHARED
    }
  }

  def getArchiveThreadingMode: ArchiveThreadingMode = {
    if (isProd) {
      ArchiveThreadingMode.DEDICATED
    } else {
      ArchiveThreadingMode.SHARED
    }
  }

  def getEgressThreadingMode: ArchiveThreadingMode = {
    if (isProd) {
      ArchiveThreadingMode.DEDICATED
    } else {
      ArchiveThreadingMode.SHARED
    }
  }

  def getIdleStrategy: IdleStrategy = {
    if (isProd) {
      YieldingIdleStrategy.INSTANCE
    } else {
      new BackoffIdleStrategy(10, 20, MICROSECONDS.toNanos(50), MILLISECONDS.toNanos(1))
    }
  }
}



