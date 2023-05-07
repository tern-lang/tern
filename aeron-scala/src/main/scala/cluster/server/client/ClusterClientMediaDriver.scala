package cluster.server.client

import cluster.server.ClusterMode
import cluster.server.client.ClusterClientMediaDriver.{DEFAULT_ARCHIVE_PATH, DEFAULT_DRIVER_PATH}
import cluster.server.group.NodeGroup
import io.aeron.CommonContext.AERON_DIR_PROP_NAME
import io.aeron.archive.{Archive, ArchiveThreadingMode, ArchivingMediaDriver}
import io.aeron.driver.{MediaDriver, ThreadingMode}
import io.aeron.{Aeron, Image}
import org.agrona.concurrent.{BackoffIdleStrategy, YieldingIdleStrategy}

import java.io.File

object ClusterClientMediaDriver {
  var DEFAULT_DATA_PATH = "data"
  var DEFAULT_DRIVER_PATH = "shared-driver"
  var DEFAULT_ARCHIVE_PATH = "shared-archive"
}

class ClusterClientMediaDriver(mode: ClusterMode, group: NodeGroup, host: String) {

  def launch(listener: ClusterClientHandler): ClusterClient = {
    val tempPath = System.getProperty("java.io.tmpdir")
    val driverPath = new File(tempPath, DEFAULT_DRIVER_PATH).getCanonicalFile
    val archivePath = new File(tempPath, DEFAULT_ARCHIVE_PATH).getCanonicalFile

    driverPath.mkdirs()
    archivePath.mkdirs()
    System.setProperty(AERON_DIR_PROP_NAME, driverPath.getCanonicalPath)

    ArchivingMediaDriver.launch(
      new MediaDriver.Context()
        .threadingMode(ThreadingMode.DEDICATED)
        .aeronDirectoryName(driverPath.getCanonicalPath)
        .conductorIdleStrategy(if (mode.isProd) YieldingIdleStrategy.INSTANCE else new BackoffIdleStrategy)
        .receiverIdleStrategy(if (mode.isProd) YieldingIdleStrategy.INSTANCE else new BackoffIdleStrategy)
        .senderIdleStrategy(if (mode.isProd) YieldingIdleStrategy.INSTANCE else new BackoffIdleStrategy)
        .termBufferSparseFile(false)
        .useWindowsHighResTimer(true)
        .warnIfDirectoryExists(false)
        .dirDeleteOnStart(true),
      new Archive.Context()
        .idleStrategySupplier(() => if (mode.isProd) YieldingIdleStrategy.INSTANCE else new BackoffIdleStrategy)
        .threadingMode(ArchiveThreadingMode.DEDICATED)
        .aeronDirectoryName(driverPath.getCanonicalPath)
        .archiveDirectoryName(archivePath.getCanonicalPath)
        .deleteArchiveOnStart(true))

    val context = new Aeron.Context()
      .errorHandler(cause => cause.printStackTrace())
      .availableImageHandler(availableImage(_))
      .unavailableImageHandler(unavailableImage(_))
      .idleStrategy(mode.getIdleStrategy)
      .awaitingIdleStrategy(mode.getIdleStrategy) // same idle control a problem?
      .aeronDirectoryName(driverPath.getCanonicalPath)

    val aeron = Aeron.connect(context)
    val inputBuffer = InputRingBuffer.create(mode)
    val outputBuffer = OutputRingBuffer.create(mode)

    ClusterClient(new ClusterConnection(listener, aeron, group, host).connect(),
      inputBuffer,
      outputBuffer)
  }

  private def availableImage(image: Image): Unit = {
    val subscription = image.subscription
    println(s"Available image on ${subscription.channel} with stream ${subscription.streamId} and session ${image.sessionId} from ${image.sourceIdentity}")
  }

  private def unavailableImage(image: Image): Unit = {
    val subscription = image.subscription
    println(s"Unavailable image on ${subscription.channel} with stream ${subscription.streamId} and session ${image.sessionId}")
  }
}


