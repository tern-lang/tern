package tern.core.function;

import tern.core.Execution;
import tern.core.Statement;
import tern.core.error.InternalStateException;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.variable.Value;

public class ModuleAccessor implements Accessor {

   private final Accessor accessor;
   private final Statement body;
   private final Module module;
   private final Scope scope;
   private final String name;
   
   public ModuleAccessor(Module module, Statement body, Scope scope, String name) {
      this.accessor = new ScopeAccessor(name, name);
      this.module = module;
      this.scope = scope;
      this.name = name;
      this.body = body;
   }
   
   @Override
   public Object getValue(Object source) {
      try {
         ScopeState state = scope.getState();
         Value field = state.getValue(name);
         
         if(field == null) {
            Execution execution = body.compile(scope, null);
            execution.execute(scope);
         }
      }catch(Exception e){
         throw new InternalStateException("Reference to '" + name + "' in '" + module + "' failed", e);
      }
      return accessor.getValue(scope);
   }

   @Override
   public void setValue(Object source, Object value) {
      try {
         ScopeState state = scope.getState();
         Value field = state.getValue(name);
         
         if(field == null) {
            Execution execution = body.compile(scope, null);
            execution.execute(scope);
         }
      }catch(Exception e){
         throw new InternalStateException("Reference to '" + name + "' in '" + module + "' failed", e);
      }   
      accessor.setValue(scope,value);
   }

}