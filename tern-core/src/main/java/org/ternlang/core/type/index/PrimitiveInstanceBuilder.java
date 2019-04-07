package org.ternlang.core.type.index;

import static org.ternlang.core.Reserved.ANY_TYPE;
import static org.ternlang.core.Reserved.DEFAULT_MODULE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.TypeNameConstraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.scope.instance.PrimitiveInstance;
import org.ternlang.core.type.Type;

public class PrimitiveInstanceBuilder {
   
   private final Constraint constraint;
   
   public PrimitiveInstanceBuilder() {
      this.constraint = new TypeNameConstraint(DEFAULT_MODULE, ANY_TYPE);
   }

   public Instance create(Scope scope, Type real) throws Exception {
      Scope inner = real.getScope();
      Type type = constraint.getType(inner);
      Module module = type.getModule();
      
      return new PrimitiveInstance(module, inner, real, type); 
   }
}