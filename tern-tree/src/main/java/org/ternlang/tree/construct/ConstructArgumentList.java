package org.ternlang.tree.construct;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.ArgumentList;

public class ConstructArgumentList {

   private final ArgumentList list;
   private final Constraint[] types;
   private final Object[] objects;
   
   public ConstructArgumentList(ArgumentList list) {
      this.types = new Constraint[]{};
      this.objects = new Object[]{};
      this.list = list;
   }

   public void define(Scope scope) throws Exception {
      if(list != null) {
         list.define(scope);
      }
   }
   
   public Constraint[] compile(Scope scope, Type type) throws Exception {
      Constraint prefix = Constraint.getConstraint(type);
      Class real = type.getType();

      if(list != null) {
         if(real == null) {
            return list.compile(scope, prefix);
         }
         return list.compile(scope);
      }
      if(real == null) {
         return new Constraint[]{prefix};
      }
      return types;
   }
   
   public Object[] create(Scope scope, Type type) throws Exception {
      Class real = type.getType();
      
      if(list != null) {
         if(real == null) {
            return list.create(scope, type);
         }
         return list.create(scope);
      }
      if(real == null) {
         return new Object[]{type};
      }
      return objects;
   }
   
}
