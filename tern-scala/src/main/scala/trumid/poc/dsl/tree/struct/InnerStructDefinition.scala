package trumid.poc.dsl.tree.struct

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import trumid.poc.model.Entity

class InnerStructDefinition(definition: StructDefinition) extends StructElement {

  override def define(scope: Scope, entity: Entity, path: Path): Unit = {
    definition.define(scope, entity.getSourceUnit)
  }

  override def include(scope: Scope, entity: Entity, path: Path): Unit = {
    definition.include(scope, entity.getSourceUnit)
  }

  override def process(scope: Scope, entity: Entity, path: Path): Unit = {
    definition.process(scope, entity.getSourceUnit)
  }

  override def extend(scope: Scope, entity: Entity, path: Path): Unit = {
    definition.extend(scope, entity.getSourceUnit)
  }
}