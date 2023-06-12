package trumid.poc.model

trait Variable {
  def isComposite: Boolean = false
  def isArray: Boolean = false
  def isPrimitive: Boolean = false
  def isString: Boolean = false
  def isOptional: Boolean = false
  def getName: String
  def getConstraint: String
}
