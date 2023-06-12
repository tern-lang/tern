package trumid.poc.cluster

trait ResultCode {
  def getCode(): String
  def getDescription(): String
}
