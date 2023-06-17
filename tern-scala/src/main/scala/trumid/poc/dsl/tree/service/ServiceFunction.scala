package trumid.poc.dsl.tree.service

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import trumid.poc.dsl.tree.union.UnionProperty
import trumid.poc.model.{Entity, Property}

class ServiceFunction(annotations: AnnotationList, identifier: TextLiteral, argument: TextLiteral, response: ServiceResponse)
  extends UnionProperty(annotations, identifier, argument) {

  protected override def update(scope: Scope, entity: Entity, path: Path, property: Property): Unit = {
    property.setResponse(response.getResponse(scope))
    property.getMask()
      .add(if(response.isReturn()) Property.RETURNS else Property.STREAMS)
  }
}
