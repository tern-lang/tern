package trumid.poc.cluster.codegen.property

import trumid.poc.model._
import trumid.poc.model.{DeclarationOrder, Domain, Entity, Mode, Property, SortedOrder}

import java.util
import java.util.{ArrayList, Collections, HashMap, List}

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
    return new StructGenerator(domain, parent, property, mode)
  }
}
