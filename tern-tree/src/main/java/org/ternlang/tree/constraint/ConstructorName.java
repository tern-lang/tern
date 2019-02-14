package org.ternlang.tree.constraint;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public class ConstructorName implements GenericName {
   
   private final Constraint type;

   public ConstructorName() {
      this(null);
   }
   
   public ConstructorName(Constraint type) {
      this.type = type;
   }

   @Override
   public String getName(Scope scope) throws Exception{ // called from outer class
      return TYPE_CONSTRUCTOR;
   }

   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      if(type != null) {
         return type.getGenerics(scope);
      }
      return EMPTY_LIST;
   }
}