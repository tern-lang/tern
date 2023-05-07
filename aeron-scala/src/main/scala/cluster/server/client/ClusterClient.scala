package cluster.server.client

case class ClusterClient(cluster: ClusterClientPoller, input: InputRingBuffer, output: OutputRingBuffer)
