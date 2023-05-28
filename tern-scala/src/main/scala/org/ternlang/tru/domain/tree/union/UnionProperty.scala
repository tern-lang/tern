package org.ternlang.tru.domain.tree.union

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.domain.tree.struct.StructElement
import org.ternlang.tru.model.Entity

class UnionProperty(annotations: AnnotationList, identifier: TextLiteral, entity: TextLiteral) extends StructElement {
  override def define(scope: Scope, entity: Entity, path: Path): Unit = {}
  override def process(scope: Scope, entity: Entity, path: Path): Unit = {}
}
