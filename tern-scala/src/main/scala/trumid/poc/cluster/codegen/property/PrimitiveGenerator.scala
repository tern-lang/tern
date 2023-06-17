package trumid.poc.cluster.codegen.property

import trumid.poc.codegen.common.SourceBuilder
import trumid.poc.model._

class PrimitiveGenerator(domain: Domain, entity: Entity, property: Property, mode: Mode) extends PropertyGenerator(domain, entity, property, mode) {

  def generateField(builder: SourceBuilder): Unit = {}

  def generateGetter(builder: SourceBuilder): Unit = {
    val constraint = property.getConstraint(mode)
    val origin = getClass.getSimpleName()
    val name = property.getName()
    val offset = getOffset()

    if (property.isOptional) {
      builder.append(s"   override def ${name}(): Option[${constraint}] = {\n")
      builder.append(s"      // ${origin}\n")
      builder.append("      this.buffer.setCount(this.offset + this.required)\n")
      builder.append(s"      if(this.buffer.getBoolean(this.offset + this.offset + ${offset})) {\n")
      builder.append(s"         Some(this.buffer.get${constraint}(this.offset + ${offset}))\n")
      builder.append(s"      } else {\n")
      builder.append(s"         None\n")
      builder.append(s"      }\n")
    }
    else {
      builder.append(s"   override def ${name}(): ${constraint} = {\n")
      builder.append(s"      // ${origin}\n")
      builder.append("      this.buffer.setCount(this.offset + this.required);\n")
      builder.append(s"      this.buffer.get${constraint}(this.offset + ${offset})\n")
    }
    builder.append("   }\n")
  }

  def generateSetter(builder: SourceBuilder): Unit = {}

  def generateGetterSignature(builder: SourceBuilder): Unit = {}

  def generateSetterSignature(builder: SourceBuilder): Unit = {}

  def generateComparisonField(builder: SourceBuilder): Unit = {}

  def generateComparison(builder: SourceBuilder): Unit = {}

  def generateComparisonSignature(builder: SourceBuilder): Unit = {}

  def generateClear(builder: SourceBuilder): Unit = {}

  def generateCopy(builder: SourceBuilder, from: String, to: String): Unit = {}

  def generateUpdate(builder: SourceBuilder, from: String, to: String, assigned: Variable): Unit = {}
}
