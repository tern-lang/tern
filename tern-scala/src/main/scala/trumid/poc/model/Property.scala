package trumid.poc.model

import org.ternlang.core.annotation.Annotation

import java.util.Map
import java.util.concurrent.ConcurrentHashMap

object Property {
  val BLANK: Int = 0
  val ARRAY: Int = 1
  val STRING: Int = 2
  val PRIMITIVE: Int = 4
  val ENTITY: Int = 8
  val ENUM: Int = 16
  val COMPOSITE: Int = 32
  val OPTIONAL: Int = 64
  val DYNAMIC: Int = 128

  def isEntity(mask: Mask): Boolean = (ENTITY & mask.value()) == ENTITY

  def isPrimitive(mask: Mask): Boolean = (PRIMITIVE & mask.value()) == PRIMITIVE

  def isArray(mask: Mask): Boolean = (ARRAY & mask.value()) == ARRAY

  def isString(mask: Mask): Boolean = (STRING & mask.value()) == STRING

  def isEnum(mask: Mask): Boolean = (ENUM & mask.value()) == ENUM

  def isComposite(mask: Mask): Boolean = (COMPOSITE & mask.value()) == COMPOSITE

  def isOptional(mask: Mask): Boolean = (OPTIONAL & mask.value()) == OPTIONAL
}

class Property(name: String) extends Variable with Annotated {
  private val annotations = new ConcurrentHashMap[String, Annotation]()
  private val attributes = new ConcurrentHashMap[String, Any]() // enum attributes mostly
  private val mask: Mask = new Mask(0)
  private var offset: PropertyOffset = new PropertyOffset(this, 0, 0)
  private var size: PropertySize = new PropertySize(this, "?", 0, 0)
  private var version: Version = _
  private var constraint: String = _
  private var default: Any = _
  private var dimension: Int = _
  private var index: Int = _

  def copy(template: Property): Property = {
    this.annotations.putAll(template.annotations)
    this.attributes.putAll(template.attributes)
    this.mask.add(template.mask)
    this.constraint = template.constraint
    this.dimension = template.dimension
    this.offset = template.offset
    this.size = template.size
    this
  }

  def getMask(): Mask = mask

  def getIndex(): Int = index

  def setIndex(index: Int): Property = {
    this.index = index
    this
  }

  def getDimension(): Int = dimension

  def setDimension(dimension: Int): Property = {
    this.dimension = dimension
    this
  }

  def getOffset(): PropertyOffset = offset

  def setOffset(offset: PropertyOffset): Property = {
    this.offset = offset
    this
  }

  def getSize(): PropertySize = size

  def setSize(size: PropertySize): Property = {
    this.size = size
    this
  }


  override def isComposite(): Boolean = Property.isComposite(mask)

  override def isArray(): Boolean = Property.isArray(mask)

  override def isPrimitive(): Boolean = Property.isPrimitive(mask)

  override def isString(): Boolean = Property.isString(mask)

  override def isOptional(): Boolean = Property.isOptional(mask)

  def getAttributes(): Map[String, Any] = attributes

  def getVersion(): Version = version

  def setVersion(version: Version): Property = {
    this.version = version
    this
  }

  def getConstraint(): String = constraint

  def getConstraint(mode: Mode): String = mode.getName(constraint, version)

  def setConstraint(constraint: String): Property = {
    this.constraint = constraint
    this
  }

  def getDefaultValue(): Any = default

  def setDefaultValue(default: Any): Property = {
    this.default = default
    this
  }

  override def getName(): String = name

  override def getAnnotations(): Map[String, Annotation] = annotations

  def isDynamic(): Boolean = false

  override def toString() = {
    name
  }
}

sealed trait PropertyOrder {
  def isDeclaration(): Boolean = false

  def isSorted(): Boolean = false
}

object DeclarationOrder extends PropertyOrder {
  override def isDeclaration(): Boolean = true
}

object SortedOrder extends PropertyOrder {
  override def isSorted(): Boolean = true
}
