package cluster.server

import io.aeron.archive.ArchiveThreadingMode
import io.aeron.driver.ThreadingMode
import org.agrona.concurrent.{BackoffIdleStrategy, IdleStrategy, YieldingIdleStrategy}

import java.util.concurrent.TimeUnit.MILLISECONDS

object TestMode extends ClusterMode
object ProdMode extends ClusterMode

sealed trait ClusterMode {

  def isTest: Boolean = this eq TestMode

  def isProd: Boolean = this eq ProdMode

  def isDeleteOnStart: Boolean = isTest

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
      new YieldingIdleStrategy
    } else {
      new BackoffIdleStrategy(10, 20, MILLISECONDS.toNanos(1), MILLISECONDS.toNanos(2))
    }
  }
}



