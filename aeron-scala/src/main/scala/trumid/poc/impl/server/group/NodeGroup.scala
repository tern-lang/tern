package trumid.poc.impl.server.group

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._

object NodeGroup {
  val CLIENT_FACING_PORT_PREFIX = 2011
  val MEMBER_FACING_PORT_PREFIX = 2022
  val LOG_PORT_PREFIX = 2033
  val TRANSFER_PORT_PREFIX = 2044
  val ARCHIVE_PORT_PREFIX = 801

  def apply(): NodeGroup = {
    val cluster = ConfigFactory.load.getConfig("cluster")
    val count = cluster.getInt("count")
    val group = cluster.getStringList("group")
      .asScala.zipWithIndex.map {
      case (address, index) => s"${index}=${address}"
    }.mkString(",")

    NodeGroup(group, count)
  }
}

case class NodeGroup(group: String, count: Int) {

  private val members = collection.mutable.LongMap[NodeMember]()
  private val addresses = NodeGroupParser.parse(group, count)

  def getMember(memberId: Int): NodeMember = {
    members.getOrElseUpdate(memberId, {
      val cnt = addresses.size

      if (memberId < 0 || memberId >= cnt) {
        throw new IllegalArgumentException(s"No such member $memberId in group $this")
      }

      NodeMember(group, memberId, cnt)
    })
  }

  def getAddress(memberId: Int): NodeAddress = {
    val cnt = addresses.size

    if (memberId < 0 || memberId >= cnt) {
      throw new IllegalArgumentException(s"No such member $memberId in group $this")
    }

    addresses(memberId)
  }

  def getAddresses(): List[NodeAddress] = {
    addresses.toList
  }

  def getClusterAddresses(): String = {
    val builder = new StringBuilder()
    for ((address, i) <- addresses.zipWithIndex) {
      if (i > 0) {
        builder.append(",")
      }
      builder.append(address.memberId)
        .append("=")
        .append(address.address)
        .append(":")
        .append(NodeGroup.CLIENT_FACING_PORT_PREFIX + i)
    }
    builder.toString()
  }

  def getClusterMembers(): String = {
    val builder = new StringBuilder()
    for ((address, i) <- addresses.zipWithIndex) {
      if (i > 0) {
        builder.append("|")
      }
      builder.append(i)
        .append(',')
        .append(address.address)
        .append(":")
        .append(NodeGroup.CLIENT_FACING_PORT_PREFIX + i)
        .append(',')
        .append(address.address)
        .append(":")
        .append(NodeGroup.MEMBER_FACING_PORT_PREFIX + i)
        .append(',')
        .append(address.address)
        .append(":")
        .append(NodeGroup.LOG_PORT_PREFIX + i)
        .append(',')
        .append(address.address)
        .append(":")
        .append(NodeGroup.TRANSFER_PORT_PREFIX + i)
        .append(',')
        .append(address.address)
        .append(":")
        .append(NodeGroup.ARCHIVE_PORT_PREFIX + i)
    }
    builder.toString()
  }

  override def toString(): String = {
    getClusterAddresses()
  }
}
