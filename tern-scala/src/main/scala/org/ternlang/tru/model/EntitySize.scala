package org.ternlang.tru.model

class EntitySize(requiredSize: Int, totalSize: Int) {
  def getRequiredSize(): Int = requiredSize
  def getTotalSize(): Int = totalSize
}
