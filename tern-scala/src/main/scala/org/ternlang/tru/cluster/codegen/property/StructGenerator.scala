package org.ternlang.tru.cluster.codegen.property

import org.ternlang.tru.codegen.common.SourceBuilder
import org.ternlang.tru.model.{Domain, Entity, Mode, Property, Variable}

class StructGenerator(domain: Domain, entity: Entity, property: Property, mode: Mode) extends PropertyGenerator(domain, entity, property, mode) {
  def generateField(builder: SourceBuilder): Unit = ???
  def generateConstructor(builder: SourceBuilder): Unit = ???
  def generateGetter(builder: SourceBuilder): Unit = ???
  def generateSetter(builder: SourceBuilder): Unit = ???
  def generateGetterSignature(builder: SourceBuilder): Unit = ???
  def generateSetterSignature(builder: SourceBuilder): Unit = ???
  def generateComparisonField(builder: SourceBuilder): Unit = ???
  def generateComparison(builder: SourceBuilder): Unit = ???
  def generateComparisonSignature(builder: SourceBuilder): Unit = ???
  def generateClear(builder: SourceBuilder): Unit = ???
  def generateCopy(builder: SourceBuilder, from: String, to: String): Unit = ???
  def generateUpdate(builder: SourceBuilder, from: String, to: String, assigned: Variable): Unit = ???
}
