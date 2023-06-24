package trumid.poc.impl.server.gateway

import trumid.poc.impl.server.client.ClusterClientOutput

case class GatewayContext(cluster: ClusterClientOutput) {
  def getClusterOutput: ClusterClientOutput = cluster
}
