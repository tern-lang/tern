package tern.core.scope.instance;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.type.Type;

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