package cluster.server.group

import java.net.InetAddress
import scala.util.matching.Regex

object NodeGroupParser {

  private val CLIENT_FACING_PORT_PREFIX = 20100

  def parse(addresses: String, count: Int): List[NodeAddress] = {
    if (addresses == null) {
      throw new NullPointerException("Nodes must not be null")
    }
    if (count != 3 && count != 1) {
      throw new IllegalStateException(s"Only a 1 or 3 node cluster is supported, $count is invalid")
    }
    val entries = addresses.trim.split("\\s*,\\s*")

    if (entries.length < 3) {
      throw new IllegalArgumentException("Membership requires at least 3 nodes")
    }
    val members = parse(entries, count)

    for (i <- members.indices) {
      val address = members(i)

      if (address.memberId != i) {
        throw new IllegalArgumentException(s"Member $i missing from group")
      }
      val port = CLIENT_FACING_PORT_PREFIX * 10 + i

      if (address.port != port) {
        throw new IllegalArgumentException(s"Member $i has ${address.port} instead of $port")
      }
    }
    members
  }

  private def parse(entries: Array[String], count: Int): List[NodeAddress] = {
    var members: List[NodeAddress] = List[NodeAddress]()

    for (i <- 0 until count) {
      val matcher: Regex.Match = "(\\d+)=(.*):(\\d+)".r.findFirstMatchIn(entries(i))
        .getOrElse(throw new IllegalArgumentException(s"Membership entries invalid ${entries(i)}"))
      val memberId = matcher.group(1).toInt
      val address = InetAddress.getByName(matcher.group(2)).getHostAddress
      val port = matcher.group(3).toInt

      members = members ++ List(NodeAddress(address, memberId, port))
    }
    members.sortBy(_.memberId)
  }
}
