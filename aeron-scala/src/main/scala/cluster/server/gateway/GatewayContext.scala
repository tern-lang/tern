package cluster.server.gateway

import cluster.server.client.ClusterClientOutput

case class GatewayContext(cluster: ClusterClientOutput) {
  def getClusterOutput: ClusterClientOutput = cluster
}
