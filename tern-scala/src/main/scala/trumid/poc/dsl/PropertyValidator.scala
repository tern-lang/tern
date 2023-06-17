package trumid.poc.dsl

import trumid.poc.model.{Category, Domain, Entity, Property}

import java.util

class PropertyValidator {

  def validate(domain: Domain): Unit = {
    val entities: util.List[Entity] = domain.getEntities()

    if (!entities.isEmpty) {
      val details: PropertyValidator.DomainDetails = new PropertyValidator.DomainDetails

      entities.forEach(entity => {
        val category: Category = entity.getCategory()

        if (category.isTable) {
          val properties: util.Set[String] = calculate(details, entity).getDynamicProperties()

          if (!properties.isEmpty) {
            throw new IllegalStateException(s"Table ${entity} has dynamic properties ${properties}")
          }
        }
      })
    }
  }

  private def calculate(domain: PropertyValidator.DomainDetails, entity: Entity): PropertyValidator.EntityDetails = {
    val name: String = entity.getName
    var details: PropertyValidator.EntityDetails = domain.getDetails(name)

    if (details == null) {
      if (!(domain.addEntity(name))) {
        throw new IllegalStateException(s"Entity ${name} has a cycle")
      }
      details = create(domain, entity)

      if (!(domain.addDetails(details))) {
        throw new IllegalStateException(s"Entity ${name} has already been calculated")
      }
    }
    return details
  }

  private def calculate(domain: PropertyValidator.DomainDetails, entity: Entity, property: Property): PropertyValidator.EntityDetails = {
    val constraint: String = property.getConstraint
    val constraintEntity: Entity = entity.getNamespace().getVisibleEntity(constraint)

    if (constraintEntity != null) {
      return calculate(domain, constraintEntity)
    }
    return new PropertyValidator.EntityDetails(constraint, util.Collections.emptySet())
  }

  private def create(domain: PropertyValidator.DomainDetails, entity: Entity): PropertyValidator.EntityDetails = {
    val name: String = entity.getName
    val category: Category = entity.getCategory()

    if (!category.isEnum()) {
      val properties: util.List[Property] = entity.getProperties()

      if (!properties.isEmpty()) {
        val dynamic = new util.HashSet[String]

        properties.forEach(property => {
          val identifier: String = property.getName()

          if (property.isArray()) {
            if (property.isDynamic()) {
              dynamic.add(identifier)
            }
          }
          val details: PropertyValidator.EntityDetails = calculate(domain, entity, property)

          if (details.isDynamic()) {
            dynamic.add(identifier)
          }
        })
        return new PropertyValidator.EntityDetails(name, dynamic)
      }
    }
    return new PropertyValidator.EntityDetails(name, util.Collections.emptySet())
  }

}
object PropertyValidator {

  private class DomainDetails() {
    final private val details = new util.HashMap[String, PropertyValidator.EntityDetails]()
    final private val done = new util.HashSet[String]()

    def getDetails(name: String): PropertyValidator.EntityDetails = {
      return details.get(name)
    }

    def addDetails(detail: PropertyValidator.EntityDetails): Boolean = {
      return this.details.put(detail.name, detail) == null
    }

    def addEntity(name: String): Boolean = {
      return done.add(name)
    }
  }

  private class EntityDetails(val name: String, val dynamic: util.Set[String]) {

    def getDynamicProperties(): util.Set[String] = {
      return dynamic
    }

    def isDynamic(): Boolean = {
      return !(dynamic.isEmpty)
    }
  }
}
