package org.ternlang.tru.model

case class Version(version: Int, latest: Boolean = false) {
  def isLatestVersion(): Boolean = latest
}
