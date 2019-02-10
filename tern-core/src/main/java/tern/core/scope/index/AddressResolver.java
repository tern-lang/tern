package tern.core.scope.index;

import static tern.core.scope.index.AddressType.INSTANCE;

import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.core.variable.bind.VariableFinder;
import tern.core.variable.bind.VariableResult;

public class AddressResolver {
   
   private final VariableFinder finder;
   private final Scope scope;

   public AddressResolver(Scope scope) {
      this.finder = new VariableFinder(null);
      this.scope = scope;
   }   

   public Address resolve(String name, int offset) {
      ScopeState state = scope.getState();
      Value value = state.getValue(name);

      if(value == null) {
         VariableResult result = resolve(scope, name);

         if(result != null) {
            return result.getAddress(offset);
         }
         return null;
      }
      return INSTANCE.getAddress(name, offset);
   }

   private VariableResult resolve(Scope scope, String name) {
      Type type = scope.getType();

      if(type != null) {
         VariableResult result = finder.findAll(scope, type, name);

         if(result != null) {
            return result;
         }
      }
      return finder.findType(scope, name);
   }
}
