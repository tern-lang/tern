package org.ternlang.tree.constraint;

import static org.ternlang.core.scope.extract.ScopePolicy.COMPILE_GENERICS;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.extract.ScopePolicyExtractor;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.AddressCache;
import org.ternlang.core.scope.index.ScopeTable;

public class GenericParameterExtractor {
   
   private final ScopePolicyExtractor extractor;
   private final GenericList generics;
   
   public GenericParameterExtractor(GenericList generics) {
      this.extractor = new ScopePolicyExtractor(COMPILE_GENERICS);
      this.generics = generics;
   }

   public Scope extract(Scope local) throws Exception {
      List<Constraint> constraints = generics.getGenerics(local);
      Scope scope = extractor.extract(local);
      ScopeTable table = scope.getTable();
      int size = constraints.size();

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         Address address = AddressCache.getAddress(i);
         
         table.addConstraint(address, constraint);
      }
      return scope;
   }
}

