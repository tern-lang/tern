package cluster.server.client

case class ClusterClient(session: ClusterSession, input: InputRingBuffer, output: OutputRingBuffer)
