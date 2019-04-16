package org.ternlang.tree.function;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.function.Signature;
import org.ternlang.core.function.SignatureAligner;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.ScopeIndex;

public class ScopeCalculator {   

   private final List<ScopeAllocation> allocations;
   private final ScopeAllocationBuilder builder;
   private final ParameterExtractor extractor;
   private final SignatureAligner aligner;
   private final Statement statement;
   private final Execution compile;

   public ScopeCalculator(Signature signature, Execution compile, Statement statement){
      this(signature, compile, statement, 0);
   }
   
   public ScopeCalculator(Signature signature, Execution compile, Statement statement, int modifiers){
      this.allocations = new ArrayList<ScopeAllocation>();
      this.extractor = new ParameterExtractor(signature, modifiers);
      this.aligner = new SignatureAligner(signature);
      this.builder = new ScopeAllocationBuilder();
      this.statement = statement;
      this.compile = compile;
   }

   public void define(Scope scope) throws Exception {
      ScopeIndex index = scope.getIndex();   

      extractor.define(scope);
      statement.define(scope);
      
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
   
   public Scope calculate(Scope scope, Object[] arguments) throws Exception {
      Object[] values = aligner.align(arguments);
      Scope inner = extractor.extract(scope, values);
      int length = allocations.size();
      
      if(compile != null) {
         compile.execute(inner); // possibly static
      }
      for(int i = length - 1; i >= 0; i--) { // largest first is more memory efficient
         ScopeAllocation allocation = allocations.get(i);
      
         if(allocation != null) {
            allocation.allocate(inner);
         }
      }
      return inner;
   }  
}

