package trumid.poc.model

trait Variable {
  def isEntity(): Boolean = false
  def isComposite(): Boolean = false
  def isArray(): Boolean = false
  def isPrimitive(): Boolean = false
  def isString(): Boolean = false
  def isOptional(): Boolean = false
  def isStreams(): Boolean = false
  def getName(): String
  def getConstraint(): String
}
