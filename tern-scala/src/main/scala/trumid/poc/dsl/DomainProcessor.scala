package trumid.poc.dsl

import trumid.poc.model.{Domain, Property, ServiceCategory}

class DomainProcessor {

  def process(domain: Domain): Unit = {
    domain.getEntities()
      .stream()
      .filter(entity => entity.getCategory().isService())
      .forEach(service => {
        val entity = service.getSourceUnit().addEntity(s"${service.getName}Response")

        entity.setCategory(ServiceCategory)
        entity.getAnnotations().putAll(service.getAnnotations())
        service.getProperties().forEach(property => {
          val identifier = property.getName()
          val response = property.getResponse()

          entity.addProperty(identifier + "Response")
            .setConstraint(response)
            .setIndex(property.getIndex)
            .getMask()
            .add(property.getMask())
        })
      })
  }
}
