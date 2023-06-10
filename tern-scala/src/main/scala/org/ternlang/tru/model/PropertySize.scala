package org.ternlang.tru.model

class PropertySize(val property: Property, val requiredSize: Int, val totalSize: Int) {
  def getIndex: Int = property.getIndex
  def getRequiredSize: Int = requiredSize
  def getTotalSize: Int = totalSize
}