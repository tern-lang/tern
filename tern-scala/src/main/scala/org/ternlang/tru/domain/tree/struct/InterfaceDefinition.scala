package org.ternlang.tru.domain.tree.struct

import org.ternlang.common.Array
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.tree.annotation.AnnotationList
import org.ternlang.tree.literal.TextLiteral
import org.ternlang.tru.model.{Entity, InterfaceCategory, Namespace}

class InterfaceDefinition(annotations: AnnotationList,
                          identifier: TextLiteral,
                          extension: StructExtension,
                          elements: Array[StructElement]) extends StructDefinition(annotations, identifier, extension, elements) {

  def this(annotations: AnnotationList, identifier: TextLiteral, elements: Array[StructElement]) {
    this(annotations, identifier, null, elements)
  }

  override def define(scope: Scope, namespace: Namespace, path: Path): Entity =
    define(scope, namespace, path, InterfaceCategory)
}
