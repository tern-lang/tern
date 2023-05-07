package cluster.server

import org.agrona.ErrorHandler
import org.agrona.concurrent.SystemEpochClock

import io.aeron.Aeron
import io.aeron.RethrowingErrorHandler
import io.aeron.cluster.service.ClusteredService
import io.aeron.cluster.service.ClusteredServiceContainer
import cluster.server.group.NodeMember
import cluster.server.log.ErrorLogger
import cluster.server.service.{ManagedService, ManagedServiceContainer}

class ClusterNode(private val mediaDriver: ClusterMediaDriver,
                  private val service: ClusteredService,
                  private val errorHandler: ErrorHandler,
                  private val member: NodeMember,
                  private val mode: ClusterMode) {

  def launch(): ClusteredServiceContainer = {
    mediaDriver.launch()

    ClusteredServiceContainer.launch(new ClusteredServiceContainer.Context()
      .aeron(Aeron.connect(new Aeron.Context()
        .idleStrategy(mode.getIdleStrategy)
        .awaitingIdleStrategy(mode.getIdleStrategy)
        .aeronDirectoryName(member.getFileSystem.getDriverPath)
        .errorHandler(errorHandler)
        .subscriberErrorHandler(RethrowingErrorHandler.INSTANCE)
        .epochClock(SystemEpochClock.INSTANCE)))
      .idleStrategySupplier(() => mode.getIdleStrategy)
      .aeronDirectoryName(member.getFileSystem.getDriverPath)
      .archiveContext(member.getArchiveContext)
      .clusterDir(member.getFileSystem.getClusterDirectory)
      .clusteredService(service)
      .errorHandler(errorHandler))
  }
}

object ClusterNode {
  def apply[T](service: ManagedService[T], mode: ClusterMode, member: NodeMember): ClusterNode = {
    val errorHandler = new ErrorLogger(s"node-${member.getMemberId}", true)
    val managedServiceContainer = new ManagedServiceContainer[T](service, member)
    val mediaDriver = new ClusterMediaDriver(mode, member)

    new ClusterNode(mediaDriver, managedServiceContainer, errorHandler, member, mode)
  }
}