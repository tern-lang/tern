package trumid.poc.model

class PropertySize(val property: Property, val comment: String, val requiredSize: Int, val totalSize: Int) {
  def getComment: String = comment
  def getIndex: Int = property.getIndex
  def getRequiredSize: Int = requiredSize
  def getTotalSize: Int = totalSize
}