package org.ternlang.core.function;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.variable.Value;

public class ScopeAccessor implements Accessor<Scope> {

   private final String alias; 
   private final String name;
   
   public ScopeAccessor(String name, String alias) {
      this.alias = alias;
      this.name = name;
   }
   
   @Override
   public Object getValue(Scope source) {
      ScopeState state = source.getState();
      Value field = state.getValue(alias);
      
      if(field == null){
         throw new InternalStateException("Field '" + name + "' does not exist");
      }
      return field.getValue();
   }

   @Override
   public void setValue(Scope source, Object value) {
      ScopeState state = source.getState();
      Value field = state.getValue(alias);
      
      if(field == null){
         throw new InternalStateException("Field '" + name + "' does not exist");
      }
      field.setValue(value);
   }

}