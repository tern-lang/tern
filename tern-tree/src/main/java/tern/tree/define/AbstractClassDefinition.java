package tern.tree.define;

import tern.core.type.TypePart;
import tern.tree.annotation.AnnotationList;

public class AbstractClassDefinition extends ClassDefinition {

   public AbstractClassDefinition(AnnotationList annotations, AbstractClassName name, TypeHierarchy hierarchy, TypePart... parts) {
      super(annotations, name, hierarchy, parts);
   }
}
