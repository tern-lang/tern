package trumid.ats.dsl.codgen.cluster.validate

import trumid.poc.model._
import trumid.poc.dsl._
import trumid.poc.codegen.common._

abstract class ValidateAnnotation(protected val domain: Domain,
                                  protected val entity: Entity,
                                  protected val property: Property,
                                  protected val mode: Mode,
                                  protected val annotation: AnnotationType) {

  def generateValidation(builder: SourceBuilder): Unit
}
