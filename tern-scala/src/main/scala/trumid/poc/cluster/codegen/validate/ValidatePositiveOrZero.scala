package trumid.poc.cluster.codegen.validate

import trumid.poc.codegen.common.SourceBuilder
import trumid.poc.common.CamelCaseStyle
import trumid.poc.dsl.AnnotationType
import trumid.poc.model.{Domain, Entity, Mode, Property}

class ValidatePositiveOrZero(domain: Domain,
                             entity: Entity,
                             property: Property,
                             mode: Mode,
                             annotation: AnnotationType)
  extends ValidateAnnotation(domain,
    entity,
    property,
    mode,
    annotation) {

  def generateValidation(builder: SourceBuilder): Unit = {
    val identifier = property.getName()
    val parameter = CamelCaseStyle.toCase(entity.getName)
    val origin = getClass.getSimpleName

    if(property.isOptional()) {
      builder.append(s"      if(${parameter}.${identifier}().isDefined && ${parameter}.${identifier}().get < 0) {\n")
      builder.append("         return ResultCode.fail(\"Invalid value for '" + identifier + "'\") // " + origin + "\n")
      builder.append(s"      }\n")
    } else {
      builder.append(s"      if(${parameter}.${identifier}() < 0) {\n")
      builder.append("         return ResultCode.fail(\"Invalid value for '" + identifier + "'\") // " + origin + "\n")
      builder.append(s"      }\n")
    }
  }

}
