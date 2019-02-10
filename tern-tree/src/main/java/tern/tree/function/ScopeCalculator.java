package tern.tree.function;

import java.util.ArrayList;
import java.util.List;

import tern.core.scope.Scope;
import tern.core.scope.index.Address;
import tern.core.scope.index.ScopeIndex;

public class ScopeCalculator {   

   private final List<ScopeAllocation> allocations;
   private final ScopeAllocationBuilder builder;
   
   public ScopeCalculator(){
      this.allocations = new ArrayList<ScopeAllocation>();
      this.builder = new ScopeAllocationBuilder();
   }

   public void define(Scope scope) throws Exception {
      ScopeIndex index = scope.getIndex();   
      
      for(Address address : index){
         ScopeAllocation allocation = builder.allocate(scope, address);
         
         if(allocation != null) {
            allocations.add(allocation);
         }
      }
   }
   
   public Scope compile(Scope scope) throws Exception {
      for(ScopeAllocation allocation : allocations) {
         allocation.compile(scope);
      }
      return scope;
   }
   
   public Scope calculate(Scope scope) throws Exception {
      for(ScopeAllocation allocation : allocations) {
         allocation.allocate(scope);
      }
      return scope;
   }  
}

