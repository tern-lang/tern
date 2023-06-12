package trumid.poc.model

sealed trait Mode {

  def isApiMode(): Boolean = false

  def isVersionedMode(): Boolean = false

  def getName(name: String, version: Version): String

  def getPackage(name: String, version: Version): String

  def getPath(name: String, version: Version): String
}

object ApiMode extends Mode {

  override def isApiMode(): Boolean = true

  override def getName(name: String, version: Version): String = name

  override def getPackage(name: String, version: Version): String = name

  override def getPath(path: String, version: Version): String = path
}

object VersionedMode extends Mode {

  override def isVersionedMode(): Boolean = true

  override def getName(name: String, version: Version): String = {
    String.format("%sV%s", name, version)
  }

  override def getPackage(name: String, version: Version): String = {
    String.format("%s.v%s", name, version)
  }

  override def getPath(path: String, version: Version): String = {
    val length = path.length
    val index = path.lastIndexOf("/")
    val name = path.substring(index + 1, length)
    val parent = path.substring(0, index)

    String.format("%s/v%s/%s", parent, version, name)
  }
}
