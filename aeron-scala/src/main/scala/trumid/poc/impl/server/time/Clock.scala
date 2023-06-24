package trumid.poc.impl.server.time

import java.util.concurrent.TimeUnit

trait Clock {
  def currentTime: Long
  def currentTime(unit: TimeUnit): Long = unit.convert(currentTime, TimeUnit.MILLISECONDS)
}

