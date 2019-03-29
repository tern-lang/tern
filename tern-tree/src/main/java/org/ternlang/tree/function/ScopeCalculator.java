package org.ternlang.tree.function;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.ScopeIndex;

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
      int length = allocations.size();
      
      for(int i = 0; i < length; i++) {
         ScopeAllocation allocation = allocations.get(i);
         
         if(allocation != null) {
            allocation.compile(scope);
         }
      }
      return scope;
   }
   
   public Scope calculate(Scope scope) throws Exception {
      int length = allocations.size();
      
      for(int i = length - 1; i >= 0; i--) { // largest first is more memory efficient
         ScopeAllocation allocation = allocations.get(i);
      
         if(allocation != null) {
            allocation.allocate(scope);
         }
      }
      return scope;
   }  
}

