package tern.tree.define;

import tern.tree.annotation.AnnotationList;
import tern.tree.constraint.AliasName;

public class AliasDefinition extends ClassDefinition {

   public AliasDefinition(AnnotationList annotations, AliasName name, TypeHierarchy hierarchy) {
      super(annotations, name, hierarchy);
   }
}

