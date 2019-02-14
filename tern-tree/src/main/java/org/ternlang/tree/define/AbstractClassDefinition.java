package org.ternlang.tree.define;

import org.ternlang.core.type.TypePart;
import org.ternlang.tree.annotation.AnnotationList;

public class AbstractClassDefinition extends ClassDefinition {

   public AbstractClassDefinition(AnnotationList annotations, AbstractClassName name, TypeHierarchy hierarchy, TypePart... parts) {
      super(annotations, name, hierarchy, parts);
   }
}
