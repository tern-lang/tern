package trumid.poc.impl.server

import io.aeron.archive.Archive
import io.aeron.cluster.{ClusteredMediaDriver, ConsensusModule}
import io.aeron.driver.{FlowControlSupplier, MediaDriver, MinMulticastFlowControlSupplier}
import org.agrona.ErrorHandler
import org.agrona.concurrent.EpochClock
import trumid.poc.impl.server.group.NodeMember

class ClusterMediaDriver(mode: ClusterMode, member: NodeMember) {

  private val flowControlSupplier: FlowControlSupplier = new MinMulticastFlowControlSupplier
  private val errorHandler: ErrorHandler = cause => cause.printStackTrace()
  private val clock: EpochClock = () => System.currentTimeMillis()

  def launch(): ClusteredMediaDriver = {
    ClusteredMediaDriver.launch(
      new MediaDriver.Context()
        .aeronDirectoryName(member.getFileSystem.getDriverPath)
        .warnIfDirectoryExists(false)
        .threadingMode(mode.getThreadingMode)
        .termBufferSparseFile(true)
        .multicastFlowControlSupplier(flowControlSupplier)
        .errorHandler(errorHandler)
        .dirDeleteOnShutdown(false)
        .dirDeleteOnStart(mode.isDeleteOnStart), // TODO not sure about this?
      new Archive.Context()
        .idleStrategySupplier(() => mode.getIdleStrategy)
        .aeronDirectoryName(member.getFileSystem.getDriverPath)
        .archiveDir(member.getFileSystem.getArchiveDirectory)
        .controlChannel(member.getArchiveControlRequestChannel)
        .controlStreamId(member.getArchiveControlRequestStreamId)
        .localControlChannel(member.getLocalControlChannel)
        .localControlStreamId(member.getArchiveControlRequestStreamId)
        .recordingEventsChannel(member.getRecordingEventsChannel)
        .recordingEventsEnabled(false)
        .threadingMode(mode.getArchiveThreadingMode)
        .errorHandler(errorHandler)
        .deleteArchiveOnStart(mode.isDeleteOnStart),
      new ConsensusModule.Context()
        .idleStrategySupplier(() => mode.getIdleStrategy)
        .epochClock(clock)
        .errorHandler(errorHandler)
        .clusterMemberId(member.getMemberId)
        .clusterMembers(member.getGroup.getClusterMembers)
        .aeronDirectoryName(member.getFileSystem.getDriverPath)
        .clusterDir(member.getFileSystem.getConsensusModuleDirectory)
        .ingressChannel(member.getIngressChannel)
        .logChannel(member.getLogChannel)
        .replicationChannel(member.getReplicationChannel)
        .archiveContext(member.getArchiveContext)
        .deleteDirOnStart(mode.isDeleteOnStart))
  }
}
