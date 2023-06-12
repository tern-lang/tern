package trumid.poc.model

class PropertyOffset(val property: Property, val requiredOffset: Int, val totalOffset: Int) {
  def getIndex: Int = property.getIndex
  def getRequiredOffset: Int = requiredOffset
  def getTotalOffset: Int = totalOffset
}