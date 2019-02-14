package org.ternlang.core.scope.instance;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class SuperExtractor {
   
   public SuperExtractor() {
      super();
   }
   
   public Type extractor(Type type) {
      List<Constraint> types = type.getTypes();
      Scope scope = type.getScope();
      
      for(Constraint base : types) {
         return base.getType(scope);
      }
      return null;
   }

}