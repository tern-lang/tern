package trumid.ats.dsl.codgen.cluster.validate

import trumid.poc.dsl._
import trumid.poc.model.{Domain, Entity, Mode, Property}

import java.util
import java.util.{HashMap, List}

class ValidateAnnotationBuilder(domain: Domain, mode: Mode, sorted: Boolean) {
  private val generators = new HashMap[Entity, List[ValidateAnnotation]]()

  def create(entity: Entity): util.List[ValidateAnnotation] = generators.computeIfAbsent(entity, (ignore: Entity) => {
    val annotations = new util.ArrayList[ValidateAnnotation]

    entity.getProperties().forEach(property => {
      property.getAnnotations().entrySet().forEach(pair => {
        val name = pair.getKey()
        val annotation = AnnotationType.resolve(name)

        annotation match {
          case Some(Positive) => annotations.add(new ValidatePositive(domain, entity, property, mode, Positive))
          case Some(PositiveOrZero) => annotations.add(new ValidatePositiveOrZero(domain, entity, property, mode, PositiveOrZero))
          case Some(Negative) => annotations.add(new ValidateNegative(domain, entity, property, mode, Negative))
          case Some(NegativeOrZero) => annotations.add(new ValidateNegativeOrZero(domain, entity, property, mode, NegativeOrZero))
          case Some(NotZero) => annotations.add(new ValidateNotZero(domain, entity, property, mode, NotZero))
          case Some(NotBlank) => annotations.add(new ValidateNotBlank(domain, entity, property, mode, NotBlank))
          case None =>
        }
      })
    })
    annotations
  })

}
