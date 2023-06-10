package org.ternlang.tru.cluster.codegen.property

import org.ternlang.tru.model.{Domain, Entity, Mode, Property, Variable}
import org.ternlang.tru.codegen.common.SourceBuilder

abstract class PropertyGenerator protected(val domain: Domain, val entity: Entity, val property: Property, val mode: Mode) {

  def getDomain(): Domain = domain
  def getProperty(): Property = property
  def getEntity(): Entity = entity

  def getOffset: String = {
    val offset = property.getOffset
    val requiredOffset = offset.getRequiredOffset
    val totalOffset = offset.getTotalOffset
    if (requiredOffset == totalOffset) return String.valueOf(requiredOffset)

    s"(singleBlock ? ${totalOffset} : ${requiredOffset})"
  }

  def getSize: String = {
    val size = property.getSize
    val requiredSize = size.getRequiredSize
    val totalSize = size.getTotalSize
    if (requiredSize == totalSize) return String.valueOf(requiredSize)

    s"(singleBlock ? ${totalSize} : ${requiredSize})"
  }

  def generateField(builder: SourceBuilder): Unit
  def generateConstructor(builder: SourceBuilder): Unit
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
