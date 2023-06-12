package trumid.poc.model

class EnumValue(val name: String, val value: String, val alias: String, val code: Int) {
  def getCode: Int = code
  def getName: String = name
  def getAlias: String = alias
  def getValue: String = value
}

