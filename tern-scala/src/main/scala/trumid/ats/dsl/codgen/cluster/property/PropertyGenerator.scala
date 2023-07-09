package trumid.ats.dsl.codgen.cluster.property

import trumid.poc.codegen.common.SourceBuilder
import trumid.poc.model.{Domain, Entity, Mode, Property, Variable}

abstract class PropertyGenerator protected(val domain: Domain, val entity: Entity, val property: Property, val mode: Mode) {

  def getDomain(): Domain = domain
  def getProperty(): Property = property
  def getEntity(): Entity = entity

  def getOffset(): String = {
    val offset = property.getOffset
    val requiredOffset = offset.getRequiredOffset
    val totalOffset = offset.getTotalOffset

    if (requiredOffset == totalOffset) {
      String.valueOf(requiredOffset)
    } else {
      s"(if (variable) ${requiredOffset} else ${totalOffset})"
    }
  }

  def getSize: String = {
    val size = property.getSize
    val requiredSize = size.getRequiredSize
    val totalSize = size.getTotalSize

    if (requiredSize == totalSize) {
      String.valueOf(requiredSize)
    } else {
      s"(if (variable) ${requiredSize} else ${totalSize})"
    }
  }

  def generateField(builder: SourceBuilder): Unit
  def generateGetter(builder: SourceBuilder): Unit
  def generateSetter(builder: SourceBuilder): Unit
  def generateGetterSignature(builder: SourceBuilder): Unit
  def generateSetterSignature(builder: SourceBuilder): Unit
  def generateComparisonField(builder: SourceBuilder): Unit
  def generateComparison(builder: SourceBuilder): Unit
  def generateComparisonSignature(builder: SourceBuilder): Unit
  def generateClear(builder: SourceBuilder): Unit
  def generateCopy(builder: SourceBuilder, from: String, to: String): Unit
  def generateUpdate(builder: SourceBuilder, from: String, to: String, assigned: Variable): Unit
}
