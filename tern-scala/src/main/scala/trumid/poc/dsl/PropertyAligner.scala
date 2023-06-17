package trumid.poc.dsl

import trumid.poc.common.Primitive
import trumid.poc.model.{Domain, Entity, EntitySize, Property, PropertyOffset, PropertySize, Version}
import trumid.poc.common.message.ByteSize
import trumid.poc.dsl.PropertyAligner._

import java.util

object PropertyAligner {
  val ARRAY_LENGTH_SIZE: Int = ByteSize.SHORT_SIZE
  val ARRAY_OFFSET_SIZE: Int = ByteSize.INT_SIZE

  val OPTIONAL_PRESENCE_SIZE: Int = ByteSize.BOOL_SIZE
  val OPTIONAL_OFFSET_SIZE: Int = ByteSize.INT_SIZE

  val ENUM_SIZE: Int = ByteSize.BYTE_SIZE

  val UNION_OPTION_SIZE: Int = ByteSize.BYTE_SIZE
  val UNION_VERSION_SIZE: Int = ByteSize.SHORT_SIZE
}

class PropertyAligner {
  private val validator: PropertyValidator = new PropertyValidator()

  def align(domain: Domain): Unit = {
    val entities: util.List[Entity] = domain.getEntities()

    if (!entities.isEmpty()) {
      val done = new util.HashSet[Entity]()

      entities.forEach(entity => {
        size(done, domain, entity)
      })
      entities.forEach(entity => {
        val name: String = entity.getName()

        if (!done.contains(entity)) {
          throw new IllegalStateException(s"Could not align ${name}")
        }
      })
    }
    entities.forEach(entity => {
      align(entity)
    })
    validator.validate(domain)
  }

  private def align(entity: Entity): Unit = {
    val properties = entity.getProperties()
    val version: Version = entity.getSourceUnit().getNamespace().getVersion()
    var totalOffset: Int = 0
    var requiredOffset: Int = 0

    properties.forEach(property => {
      val offset = new PropertyOffset(property, requiredOffset, totalOffset)

      property.setVersion(version)
      property.setOffset(offset)

      val identifier: String = property.getName
      val size: PropertySize = property.getSize

      if (size == null) {
        throw new IllegalStateException(s"Could not determine size of ${identifier} for ${entity}")
      }
      totalOffset += size.getTotalSize
      requiredOffset += size.getRequiredSize
    })
  }

  private def size(done: util.Set[Entity], domain: Domain, entity: Entity): EntitySize = {
    if (done.add(entity)) {
      val category = entity.getCategory()
      val name: String = entity.getName()
      var requiredSize: Int = 0
      var totalSize: Int = 0

      if (category.isEnum) {
        val properties = entity.getProperties()

        requiredSize = ENUM_SIZE
        totalSize = requiredSize

        properties.forEach(property => {
          val size: PropertySize = new PropertySize(property, String.valueOf(ENUM_SIZE), ENUM_SIZE, ENUM_SIZE)
          property.setSize(size)
        })
      } else {
        if (category.isUnion) {
          entity.getProperties().forEach(property => {
            val constraint: String = property.getConstraint
            val option: Entity = entity.getNamespace().getVisibleEntity(constraint)

            if (option == null) {
              throw new IllegalStateException("Entity '" + constraint + "' in '" + name + "' is not defined or is a primitive")
            }
            val entitySize: EntitySize = size(done, domain, option)
            requiredSize = Math.max(requiredSize, entitySize.getRequiredSize())
          })
          requiredSize += UNION_VERSION_SIZE + UNION_OPTION_SIZE // select union

          totalSize = requiredSize
          entity.getProperties().forEach(property => {
            val size: PropertySize = new PropertySize(property, String.valueOf(totalSize), requiredSize, totalSize)
            property.setSize(size)
          })
        }
        else {
          entity.getProperties().forEach(property => {
            val constraint: String = property.getConstraint
            val constraintEntity: Entity = entity.getNamespace().getVisibleEntity(constraint)

            if (constraintEntity != null) {
              size(done, domain, constraintEntity)
            }
          })
          entity.getProperties().forEach(property => {
            val propertySize: PropertySize = size(domain, entity, property)

            property.setSize(propertySize)
            requiredSize += propertySize.getRequiredSize
            totalSize += propertySize.getTotalSize
          })
        }
      }
      entity.setSize(new EntitySize(requiredSize, totalSize))
    }
    return entity.getSize
  }

  def size(domain: Domain, parent: Entity, property: Property): PropertySize = {
    val constraint: String = property.getConstraint
    val category = parent.getCategory()

    if (category.isTable) {
      if (property.isDynamic) {
        throw new IllegalStateException("Table " + parent + " has illegal dynamic property '" + property + "'")
      }
      if (property.isArray() && property.isDynamic()) {
        throw new IllegalStateException("Table " + parent + " has illegal dynamic array property '" + property + "'")
      }
    }
    if (!(property.isPrimitive)) {
      val child: Entity = parent.getNamespace().getVisibleEntity(constraint)

      if (child == null) {
        throw new IllegalStateException("Could not find entity '" + constraint + "'")
      }
      val childType = child.getCategory()

      if (childType.isEnum()) {
        return sizeOfEnum(parent, property, constraint)
      }
      return sizeOfStruct(parent, property, constraint)
    }
    else {
      return sizeOfPrimitive(parent, property, constraint)
    }
  }

  def sizeOfStruct(parent: Entity, property: Property, constraint: String): PropertySize = {
    val child: Entity = parent.getNamespace().getVisibleEntity(constraint)
    if (child == null) {
      throw new IllegalStateException(s"Could not find entity '${constraint}'")
    }
    val entitySize: EntitySize = child.getSize()

    if (property.isArray) {
      val dimension: Int = property.getDimension()
      var elementSize: Int = entitySize.getTotalSize()

      elementSize += ARRAY_OFFSET_SIZE
      elementSize *= dimension

      if (property.isOptional) {
        val description = s"(${entitySize.getTotalSize() + ARRAY_OFFSET_SIZE} x ${dimension}) "+
          s"+ ${OPTIONAL_PRESENCE_SIZE} + ${OPTIONAL_OFFSET_SIZE} + ${ARRAY_LENGTH_SIZE}"

        return new PropertySize(property, description, OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE,
          OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE + ARRAY_LENGTH_SIZE + ARRAY_OFFSET_SIZE + elementSize)
      } else {
        val description = s"(${entitySize.getTotalSize() + ARRAY_OFFSET_SIZE} x ${dimension}) "+
          s"+ ${ARRAY_LENGTH_SIZE}"

        return new PropertySize(property, description, ARRAY_LENGTH_SIZE + ARRAY_OFFSET_SIZE, ARRAY_LENGTH_SIZE + ARRAY_OFFSET_SIZE + elementSize)
      }
    }
    if (property.isOptional) {
      val description = s"${entitySize.getTotalSize()} "+
        s"+ ${OPTIONAL_PRESENCE_SIZE} + ${OPTIONAL_OFFSET_SIZE}"

      return new PropertySize(property, description,
        OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE, OPTIONAL_PRESENCE_SIZE +
          OPTIONAL_OFFSET_SIZE + entitySize.getTotalSize())
    } else {
      return new PropertySize(property, String.valueOf(entitySize.getTotalSize()),
        entitySize.getRequiredSize(), entitySize.getTotalSize())
    }
  }

  def sizeOfEnum(parent: Entity, property: Property, constraint: String): PropertySize = {
    val size: Int = ENUM_SIZE

    if (property.isArray) {
      val dimension: Int = property.getDimension()
      val arraySize: Int = (dimension * size) * ByteSize.SHORT_SIZE
      val requiredBytes: Int = ARRAY_LENGTH_SIZE + ARRAY_OFFSET_SIZE
      val totalBytes: Int = (arraySize) + ARRAY_LENGTH_SIZE + ARRAY_OFFSET_SIZE

      if (property.isOptional) {
        val description = s"(${dimension} * (${size} + ${ByteSize.SHORT_SIZE})) " +
          s"+ ${ARRAY_LENGTH_SIZE} + ${ARRAY_OFFSET_SIZE}" +
          s"+ ${OPTIONAL_PRESENCE_SIZE} + ${OPTIONAL_OFFSET_SIZE}"

        return new PropertySize(property, description, OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE
          + requiredBytes, OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE + totalBytes)
      } else {
        val description = s"(${dimension} * (${size} + ${ByteSize.SHORT_SIZE})) " +
          s"+ ${ARRAY_LENGTH_SIZE} + ${ARRAY_OFFSET_SIZE}"

        return new PropertySize(property, description, requiredBytes, totalBytes)
      }
    }
    if (property.isOptional) {
      val description = s"${size} ${OPTIONAL_PRESENCE_SIZE} + ${OPTIONAL_OFFSET_SIZE}"
      return new PropertySize(property, description, OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE + size, OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE + size)
    } else {
      return new PropertySize(property, String.valueOf(size), size, size)
    }
  }

  def sizeOfPrimitive(parent: Entity, property: Property, constraint: String): PropertySize = {
    val primitive: Primitive = Primitive.resolve(constraint).get
    val size: Int = primitive.size()

    if (property.isArray) {
      val dimension: Int = property.getDimension()
      val arraySize: Int = (dimension * size) * ByteSize.SHORT_SIZE
      val requiredBytes: Int = ARRAY_LENGTH_SIZE + ARRAY_OFFSET_SIZE
      val totalBytes: Int = (arraySize) + ARRAY_LENGTH_SIZE + ARRAY_OFFSET_SIZE

      if (property.isOptional) {
        val description = s"(${dimension} * (${size} + ${ByteSize.SHORT_SIZE})) " +
          s"+ ${ARRAY_LENGTH_SIZE} + ${ARRAY_OFFSET_SIZE}" +
          s"+ ${OPTIONAL_PRESENCE_SIZE} + ${OPTIONAL_OFFSET_SIZE}"

        return new PropertySize(property, description, OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE
          + requiredBytes, OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE + totalBytes)
      } else {
        val description = s"(${dimension} * (${size} + ${ByteSize.SHORT_SIZE})) " +
          s"+ ${ARRAY_LENGTH_SIZE} + ${ARRAY_OFFSET_SIZE}"

        return new PropertySize(property, description, requiredBytes, totalBytes)
      }
    }
    if (property.isOptional) {
      val description = s"${size} ${OPTIONAL_PRESENCE_SIZE} + ${OPTIONAL_OFFSET_SIZE}"
      return new PropertySize(property, description, OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE + size,
        OPTIONAL_PRESENCE_SIZE + OPTIONAL_OFFSET_SIZE + size)
    } else {
      return new PropertySize(property, String.valueOf(size), size, size)
    }
  }
}
