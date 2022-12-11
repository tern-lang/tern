package org.ternlang.tree;

import static org.ternlang.core.Expansion.CLOSURE;
import static org.ternlang.core.Expansion.NORMAL;

import org.ternlang.core.Expansion;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class ArgumentList {
   
   private final Constraint[] none;
   private final Argument[] list;
   private final Object[] empty;
   
   public ArgumentList(Argument... list) {
      this.none = new Constraint[]{};
      this.empty = new Object[]{};
      this.list = list;
   }
   
   public int define(Scope scope) throws Exception{
      for(int i = 0; i < list.length; i++){
         list[i].define(scope);
      }
      return list.length;
   }


   public Expansion expansion(Scope scope) throws Exception {
      for(int i = 0; i < list.length; i++){
         if(list[i].expansion(scope).isClosure()) {
            return CLOSURE;
         }
      }
      return NORMAL;
   }
   
   public Constraint[] compile(Scope scope, Constraint... prefix) throws Exception{
      if(list.length + prefix.length > 0) {
         Constraint[] values = new Constraint[list.length + prefix.length];
         
         for(int i = 0; i < list.length; i++) {
            values[i + prefix.length] = list[i].compile(scope, null);
         }
         for(int i = 0; i < prefix.length; i++) {
            values[i] = prefix[i];
         }
         return values;
      }
      return none;
   }

   public Object[] create(Scope scope, Object... prefix) throws Exception{
      if(list.length + prefix.length > 0) {
         Object[] values = new Object[list.length + prefix.length];
         
         for(int i = 0; i < list.length; i++) {
            Value reference = list[i].evaluate(scope, null);
            Object result = reference.getValue();
            
            values[i + prefix.length] = result;
         }
         for(int i = 0; i < prefix.length; i++) {
            values[i] = prefix[i];
         }
         return values;
      }
      return empty;
   }
}