package org.ternlang.tru.domain.tree.union

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tru.domain.tree.struct.StructElement
import org.ternlang.tru.model.Entity

class InnerUnionDefinition(definition: UnionDefinition) extends StructElement {

  override def define(scope: Scope, entity: Entity, path: Path): Unit = {
    definition.define(scope, entity.getNamespace, path)
  }

  override def include(scope: Scope, entity: Entity, path: Path): Unit = {
    definition.include(scope, entity.getNamespace, path)
  }

  override def process(scope: Scope, entity: Entity, path: Path): Unit = {
    definition.process(scope, entity.getNamespace, path)
  }

  override def extend(scope: Scope, entity: Entity, path: Path): Unit = {
    definition.extend(scope, entity.getNamespace, path)
  }
}
