package trumid.poc.impl.server.common

import scala.collection.JavaConverters._

class ThreadDumper {

  def dumpThreads(): String = {
    val stackTraces = Thread.getAllStackTraces.asScala
    generateDump(stackTraces)
  }

  def dumpCurrentThread(): String = {
    val currentThread = Thread.currentThread
    val stackTrace = currentThread.getStackTrace
    val stackTraces = scala.collection.mutable.Map(currentThread -> stackTrace)
    generateDump(stackTraces)
  }

  private def generateDump(stackTraces:scala.collection.mutable.Map[Thread, Array[StackTraceElement]]): String = {
    val builder = new StringBuilder

    builder.append("\n")

    for ((thread, stackElements) <- stackTraces) {
      generateDescription(thread, builder)
      generateStackFrames(stackElements, builder)
    }

    builder.toString
  }

  private def generateStackFrames(stackElements: Array[StackTraceElement], builder: StringBuilder): Unit = {
    for (stackTraceElement <- stackElements) {
      builder.append("    at ")
      builder.append(stackTraceElement)
      builder.append("\n")
    }
  }

  private def generateDescription(thread: Thread, builder: StringBuilder): Unit = {
    val threadState = thread.getState
    val threadName = thread.getName
    val threadId = thread.getId

    builder.append("\n")
    builder.append(threadName)
    builder.append(" Id=")
    builder.append(threadId)
    builder.append(" in ")
    builder.append(threadState)
    builder.append("\n")
  }
}

