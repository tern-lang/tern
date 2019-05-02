package org.ternlang.core.scope.index;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.variable.Value;

public class LocalScopeFinder {
   
   private final LocalScopeChecker checker;
   
   public LocalScopeFinder() {
      this.checker = new LocalScopeChecker();
   }   

   public Value findValue(Scope scope, String name) {
      return findValue(scope, name, null);
   }

   public Value findValue(Scope scope, String name, Address address) {
      if(address == null){
         ScopeState state = scope.getState();
         Value value = state.getValue(name);
            
         if(checker.isValid(value)) { 
            return value;
         }
      }else {
         ScopeTable table = scope.getTable();
         Value value = table.getValue(address);

         if(checker.isValid(value)) { 
            return value;
         }
      }
      return null;
   }
   
   public Value findFunction(Scope scope, String name) {
      return findFunction(scope, name, null);
   }
   
   public Value findFunction(Scope scope, String name, Address address) {
      if(address == null){
         ScopeState state = scope.getState();
         Value value = state.getValue(name);
         
         if(!checker.isGenerated(value)) { 
            return value;
         }
      }else {
         ScopeTable table = scope.getTable();
         Value value = table.getValue(address);

         if(!checker.isGenerated(value)) { 
            return value;
         }
      }
      return null;
   }
}
