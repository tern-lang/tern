package org.ternlang.tree.define;

import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.constraint.AliasName;

public class AliasDefinition extends ClassDefinition {

   public AliasDefinition(AnnotationList annotations, AliasName name, TypeHierarchy hierarchy) {
      super(annotations, name, hierarchy);
   }
}

