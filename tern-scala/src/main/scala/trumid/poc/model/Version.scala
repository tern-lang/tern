package trumid.poc.model

case class Version(version: Int, latest: Boolean = false) {
  def isLatestVersion(): Boolean = latest
}
