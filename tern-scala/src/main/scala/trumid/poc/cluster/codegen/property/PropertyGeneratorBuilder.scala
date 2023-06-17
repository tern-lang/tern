package trumid.poc.cluster.codegen.property

import trumid.poc.model._

import java.util
import java.util.{Collections, HashMap, List}

class PropertyGeneratorBuilder(domain: Domain, mode: Mode, sorted: Boolean) {
  private val generators = new HashMap[Entity, List[PropertyGenerator]]()

  def create(entity: Entity): util.List[PropertyGenerator] = generators.computeIfAbsent(entity, (ignore: Entity) => {
    val order = if (sorted) SortedOrder else DeclarationOrder
    val properties: util.List[Property] = entity.getProperties(order)

    if (!properties.isEmpty) {
      val generators: util.List[PropertyGenerator] = new util.ArrayList[PropertyGenerator]

      properties.forEach(property => {
        val generator: PropertyGenerator = create(entity, property)
        generators.add(generator)
      })
      return Collections.unmodifiableList(generators)
    }
    Collections.emptyList[PropertyGenerator]
  })

  private def create(parent: Entity, property: Property): PropertyGenerator = {
    if (property.isPrimitive()) {
      if(property.isArray()) {
        return new PrimitiveArrayGenerator(domain, parent, property, mode)
      } else {
        return new PrimitiveGenerator(domain, parent, property, mode)
      }
    } else {
      val identifier = property.getName()
      val constraint = property.getConstraint()

      if (constraint == null) {
        throw new IllegalStateException(s"Could not determine constraint for '${identifier}'")
      }
      val entity = parent.getSourceUnit().getNamespace().getVisibleEntity(constraint)

      if (entity == null) {
        throw new IllegalStateException("Could not find entity " + entity)
      }
      val category = entity.getCategory()

      if (category.isEnum()) {
        return new EnumGenerator(domain, parent, property, mode)
      } else {
        return new StructGenerator(domain, parent, property, mode)
      }
    }
  }
}
