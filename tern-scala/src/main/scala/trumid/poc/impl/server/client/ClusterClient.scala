package trumid.poc.impl.server.client

case class ClusterClient(cluster: ClusterClientPoller, input: InputRingBuffer, output: OutputRingBuffer)
