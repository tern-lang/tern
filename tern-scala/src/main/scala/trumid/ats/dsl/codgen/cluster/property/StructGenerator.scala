package trumid.ats.dsl.codgen.cluster.property

import trumid.poc.codegen.common.SourceBuilder
import trumid.poc.model.{Domain, Entity, Mode, Property, Variable}

class StructGenerator(domain: Domain, entity: Entity, property: Property, mode: Mode) extends PropertyGenerator(domain, entity, property, mode) {

  def generateField(builder: SourceBuilder): Unit = {
    val name = property.getName()
    val size = property.getSize()
    val constraint = property.getConstraint(mode)

    builder.append(s"   private val ${name}Codec: ${constraint}Codec = new ${constraint}Codec(variable) // ${size.getComment}\n")
  }

  def generateGetter(builder: SourceBuilder): Unit = {
    val constraint = property.getConstraint(mode)
    val origin = getClass.getSimpleName()
    val name = property.getName()
    val offset = getOffset()

    if (property.isOptional) {
      builder.append(s"   override def ${name}(): Option[${constraint}] = {\n")
      builder.append(s"      // ${origin}\n")
      builder.append("      this.buffer.setCount(this.offset + this.required)\n")
      builder.append(s"      if (this.buffer.getBoolean(this.offset + this.offset + ${offset})) {\n")
      builder.append(s"         Some(this.${name}Codec.assign(this.buffer, this.offset + ${offset}, this.length - ${offset}))\n")
      builder.append(s"      } else {\n")
      builder.append(s"         None\n")
      builder.append(s"      }\n")
    }
    else {
      builder.append(s"   override def ${name}(): ${constraint} = {\n")
      builder.append(s"      // ${origin}\n")
      builder.append("      this.buffer.setCount(this.offset + this.required);\n")
      builder.append(s"      this.${name}Codec.assign(this.buffer, this.offset + ${offset}, this.length - ${offset})\n")
    }
    builder.append("   }\n")
  }


  def generateSetter(builder: SourceBuilder): Unit = {
    val constraint = property.getConstraint(mode)
    val origin = getClass.getSimpleName()
    val name = property.getName()
    val parent = entity.getName(mode)
    val offset = getOffset()

    if (property.isOptional) {
      builder.append(s"   override def ${name}(${name}: (OptionBuilder[${constraint}Builder]) => Unit): ${parent}Builder = {\n")
      builder.append(s"      // ${origin}\n")
      builder.append(s"      ${name}.apply(OptionBuilder[${constraint}Builder](\n")
      builder.append(s"        some = () => {\n")
      builder.append(s"           this.buffer.setCount(this.offset + this.required)\n")
      builder.append(s"           this.buffer.setBoolean(this.offset + ${offset}, true)\n")
      builder.append(s"           this.${name}Codec.assign(this.buffer, this.offset + ${offset}, this.length - ${offset}).defaults()\n")
      builder.append(s"        },\n")
      builder.append(s"        none = () => {\n")
      builder.append(s"           this.buffer.setCount(this.offset + this.required)\n")
      builder.append(s"           this.buffer.setBoolean(this.offset + ${offset}, false)\n")
      builder.append(s"        }\n")
      builder.append(s"      ))\n")
      builder.append(s"      this\n")
    }
    else {
      builder.append(s"   override def ${name}(${name}: (${constraint}Builder) => Unit): ${parent}Builder = {\n")
      builder.append(s"      // ${origin}\n")
      builder.append("      this.buffer.setCount(this.offset + this.required);\n")
      builder.append(s"      ${name}.apply(this.${name}Codec.assign(this.buffer, this.offset + ${offset}, this.length - ${offset}).defaults())\n")
      builder.append(s"      this\n")
    }
    builder.append("   }\n")
  }


  def generateGetterSignature(builder: SourceBuilder): Unit = {
    val constraint = property.getConstraint(mode)
    val origin = getClass.getSimpleName()
    val name = property.getName()

    if (property.isOptional) {
      builder.append(s"   def ${name}(): Option[${constraint}] // ${origin}\n")
    } else {
      builder.append(s"   def ${name}(): ${constraint} // ${origin}\n")
    }
  }

  def generateSetterSignature(builder: SourceBuilder): Unit = {
    val constraint = property.getConstraint(mode)
    val origin = getClass.getSimpleName()
    val name = property.getName()
    val parent = entity.getName(mode)

    if(property.isOptional()) {
      builder.append(s"   def ${name}(${name}: (OptionBuilder[${constraint}Builder]) => Unit): ${parent}Builder // ${origin}\n")
    } else {
      builder.append(s"   def ${name}(${name}: (${constraint}Builder) => Unit): ${parent}Builder // ${origin}\n")
    }
  }

  def generateComparisonField(builder: SourceBuilder): Unit = {}

  def generateComparison(builder: SourceBuilder): Unit = {}

  def generateComparisonSignature(builder: SourceBuilder): Unit = {}

  def generateClear(builder: SourceBuilder): Unit = {}

  def generateCopy(builder: SourceBuilder, from: String, to: String): Unit = {}

  def generateUpdate(builder: SourceBuilder, from: String, to: String, assigned: Variable): Unit = {}
}
