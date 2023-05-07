package cluster.server.group

import io.aeron.CommonContext

import java.io.File

case class NodeFileSystem(memberId: Int) {

  import NodeFileSystem._

  val basePath: String = NodeFileSystem.getClusterBasePath(CommonContext.getAeronDirectoryName, memberId).getAbsolutePath
  val driverPath: String = NodeFileSystem.getDriverPath(CommonContext.getAeronDirectoryName, memberId).getAbsolutePath
  val egressPath: String = NodeFileSystem.getEgressPath(CommonContext.getAeronDirectoryName, memberId).getAbsolutePath

  def getBasePath(): String = basePath

  def getDriverPath(): String = driverPath

  def getClusterDirectory(): File = new File(basePath, CLUSTER_DIRECTORY).getAbsoluteFile

  def getConsensusModuleDirectory(): File = new File(basePath, CONSENSUS_MODULE_PATH).getAbsoluteFile

  def getArchiveDirectory(): File = new File(basePath, ARCHIVE_DIRECTORY).getAbsoluteFile

  def getEgressDriverPath(): File = new File(egressPath, DRIVER_DIRECTORY).getAbsoluteFile

  def getEgressArchiveDirectory(): File = new File(egressPath, ARCHIVE_DIRECTORY).getAbsoluteFile
}

object NodeFileSystem {

  val CONSENSUS_MODULE_PATH = "consensus-module"
  val CLUSTER_DIRECTORY = "service"
  val ARCHIVE_DIRECTORY = "archive"
  val DRIVER_DIRECTORY = "driver"

  def getClusterBasePath(aeronPrefix: String, memberId: Int): File =
    new File(s"$aeronPrefix-$memberId").getAbsoluteFile

  def getClusterPath(aeronPrefix: String, memberId: Int, name: String): File =
    new File(getClusterBasePath(aeronPrefix, memberId), name).getAbsoluteFile

  def getDriverPath(aeronPrefix: String, memberId: Int): File =
    new File(s"$aeronPrefix-$memberId-driver").getAbsoluteFile

  def getEgressPath(aeronPrefix: String, memberId: Int): File =
    new File(s"$aeronPrefix-$memberId-egress").getAbsoluteFile
}
