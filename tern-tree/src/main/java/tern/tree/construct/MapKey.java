package tern.tree.construct;

import java.util.concurrent.atomic.AtomicReference;

import tern.core.Evaluation;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.scope.index.Address;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.variable.Value;
import tern.tree.NameReference;

public class MapKey extends Evaluation {
   
   private final AtomicReference<Address> location;
   private final NameReference reference;
   
   public MapKey(Evaluation key) {
      this.location = new AtomicReference<Address>();
      this.reference = new NameReference(key);
   }
   
   @Override
   public void define(Scope scope) throws Exception{
      String name = reference.getName(scope);
      ScopeIndex index = scope.getIndex();
      Address address = index.get(name);

      location.set(address);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception{
      String name = reference.getName(scope);
      Address address = location.get();
      
      if(address == null){
         ScopeState state = scope.getState(); 
         Value value = state.getValue(name);
         
         if(value != null) { 
            return value;
         }
      }else {
         ScopeTable table = scope.getTable(); // here we use the stack
         Value value = table.getValue(address);
         
         if(value != null) { 
            return value;
         }
      }
      return Value.getTransient(name);
   }
}