package cluster.server.group

case class NodeAddress(address: String, memberId: Int, port: Int) {

  def getAddress() = address

  def getMemberId() = memberId

  def getPort() = port

  override def toString() = {
    s"$memberId=$address:$port"
  }
}
