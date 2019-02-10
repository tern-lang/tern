package tern.core.type.index;

import static tern.core.Reserved.ANY_TYPE;
import static tern.core.Reserved.DEFAULT_PACKAGE;

import tern.core.constraint.Constraint;
import tern.core.constraint.TypeNameConstraint;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.scope.instance.PrimitiveInstance;
import tern.core.type.Type;

public class PrimitiveInstanceBuilder {
   
   private final Constraint constraint;
   
   public PrimitiveInstanceBuilder() {
      this.constraint = new TypeNameConstraint(DEFAULT_PACKAGE, ANY_TYPE);
   }

   public Instance create(Scope scope, Type real) throws Exception {
      Scope inner = real.getScope();
      Type type = constraint.getType(inner);
      Module module = type.getModule();
      
      return new PrimitiveInstance(module, inner, real, type); 
   }
}