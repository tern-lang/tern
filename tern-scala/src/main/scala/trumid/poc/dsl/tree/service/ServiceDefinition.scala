package trumid.poc.dsl.tree.service

import org.ternlang.common.Array
import org.ternlang.core.scope.Scope
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import trumid.poc.dsl.tree.struct.StructElement
import trumid.poc.dsl.tree.union.UnionDefinition
import trumid.poc.model.{Entity, ServiceCategory, SourceUnit}

class ServiceDefinition(annotations: AnnotationList,
                        identifier: TextLiteral,
                        requirement: ServiceRequirement,
                        properties: Array[StructElement]) extends UnionDefinition(annotations, identifier, requirement, properties) {

  def this(annotations: AnnotationList, identifier: TextLiteral, properties: Array[StructElement]) {
    this(annotations, identifier, null, properties)
  }

  protected override def update(scope: Scope, unit: SourceUnit, entity: Entity): Unit = {
    entity.setCategory(ServiceCategory)
  }
}

