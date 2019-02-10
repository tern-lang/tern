package tern.core.function;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.variable.Value;

public class StaticAccessor implements Accessor {

   private final Accessor accessor;
   private final TypeBody body;
   private final String alias;
   private final String name;
   private final Type type;
   
   public StaticAccessor(TypeBody body, Type type, String name, String alias) {
      this.accessor = new ScopeAccessor(name, alias);
      this.alias = alias;
      this.body = body;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public Object getValue(Object source) {
      Scope scope = type.getScope();
      ScopeState state = scope.getState();
      Value field = state.getValue(alias);
      
      try {
         if(field == null) {
            body.allocate(scope, type);           
         }
      }catch(Exception e){
         throw new InternalStateException("Static reference to '" + name + "' in '" + type + "' failed", e);
      }
      return accessor.getValue(scope);
   }

   @Override
   public void setValue(Object source, Object value) {
      Scope scope = type.getScope();
      ScopeState state = scope.getState();
      Value field = state.getValue(alias);
      
      try {
         if(field == null) {
            body.allocate(scope, type);           
         }    
      }catch(Exception e){
         throw new InternalStateException("Static reference to '" + name + "' in '" + type + "' failed", e);
      }   
      accessor.setValue(scope,value);
   }

}