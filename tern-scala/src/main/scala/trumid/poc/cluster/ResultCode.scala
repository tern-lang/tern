package trumid.poc.cluster

trait ResultCode {
  def code(): CharSequence
  def reason(): CharSequence
  def success(): Boolean
}

object ResultCode {
  val OK: ResultCode = success("Ok")

  def fail(reason: String): ResultCode = {
    BasicResultCode(reason, true)
  }

  def success(reason: String): ResultCode = {
    BasicResultCode(reason, false)
  }

  private case class BasicResultCode(text: String, fail: Boolean) extends ResultCode {
    override def code(): String = text
    override def reason(): String = text
    override def success(): Boolean = !fail
  }
}
