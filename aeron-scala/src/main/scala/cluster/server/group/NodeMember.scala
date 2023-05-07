package cluster.server.group

import com.typesafe.config.ConfigFactory
import io.aeron.archive.client.AeronArchive

import scala.collection.JavaConverters._

object NodeMember {

  def apply(): NodeMember = {
    val cluster = ConfigFactory.load.getConfig("cluster")
    val count = cluster.getInt("count")
    val memberId = cluster.getInt("memberId")
    val group = cluster.getStringList("group")
      .asScala.zipWithIndex.map {
      case (address, index) => s"${index}=${address}"
    }.mkString(",")

    apply(group, memberId, count)
  }

  def apply(group: String, memberId: Int, memberCount: Int): NodeMember = {
    NodeMember(NodeFileSystem(memberId), NodeGroup(group, memberCount), memberId)
  }
}

case class NodeMember(fileSystem: NodeFileSystem, group: NodeGroup, memberId: Int) {

  def getMemberId(): Int = memberId

  def getEgressPort(): Int = 8060 + memberId

  def getEgressPublishPort(): Int = 8070 + memberId

  def getEgressStreamId(): Int = 1030

  def getEgressName(): String = "egress"

  def getGroup(): NodeGroup = group

  def getFileSystem(): NodeFileSystem = fileSystem

  def getAddress(): NodeAddress = group.getAddress(memberId)

  def getIngressChannel(): String = "aeron:udp?term-length=256k"

  def getLogChannel(): String = {
    val address = getAddress().getAddress()
    val memberId = getMemberId()
    s"aeron:udp?term-length=256k|control-mode=manual|control=$address:5555$memberId"
  }

  def getLocalControlChannel(): String = "aeron:ipc?term-length=256k"

  def getReplicationChannel(): String = {
    val address = getAddress().getAddress()
    val memberId = getMemberId()
    s"aeron:udp?endpoint=$address:0" // maybe 4444%s
  }

  def getRecordingEventsChannel(): String = {
    val address = getAddress().getAddress()
    val memberId = getMemberId()
    s"aeron:udp?control-mode=dynamic|control=$address:803$memberId"
  }

  def getArchiveControlRequestChannel(): String = {
    val address = getAddress().getAddress()
    val memberId = getMemberId()
    s"aeron:udp?term-length=256k|endpoint=$address:801$memberId"
  }

  def getArchiveControlRequestStreamId(): Int = 100 + getMemberId()

  def getArchiveControlResponseStreamId(): Int = 110 + getMemberId()

  def getArchiveContext(): AeronArchive.Context = {
    new AeronArchive.Context()
      .controlRequestChannel(getLocalControlChannel())
      .controlRequestStreamId(getArchiveControlRequestStreamId())
      .controlResponseChannel(getLocalControlChannel())
      .controlResponseStreamId(getArchiveControlResponseStreamId())
      .aeronDirectoryName(getFileSystem().getBasePath())
  }

  override def toString(): String = String.valueOf(memberId)
}

