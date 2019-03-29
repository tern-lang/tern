package org.ternlang.tree.compile;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.extract.ScopePolicyExtractor;
import org.ternlang.core.scope.extract.ScopePolicy;
import org.ternlang.core.type.Type;
import org.ternlang.tree.constraint.GenericList;

public class FunctionScopeCompiler extends ScopeCompiler {

   protected final ScopePolicyExtractor extractor;
   protected final GenericList generics;

   public FunctionScopeCompiler(GenericList generics, ScopePolicy policy) {
      this.extractor = new ScopePolicyExtractor(policy);
      this.generics = generics;
   }

   @Override
   public Scope define(Scope local, Type type) throws Exception{
      List<Constraint> constraints = generics.getGenerics(local);
      Scope scope = extractor.extract(local);
      ScopeState state = scope.getState();
      int size = constraints.size();

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         String name = constraint.getName(scope);

         state.addConstraint(name, constraint);
      }
      return scope;
   }

   @Override
   public Scope compile(Scope local, Type type, Function function) throws Exception {
      List<Constraint> constraints = generics.getGenerics(local);
      Scope scope = extractor.extract(local);
      ScopeState state = scope.getState();
      int size = constraints.size();

      compileParameters(scope, function);
      compileProperties(scope, type);   
      
      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         String name = constraint.getName(scope);

         state.addConstraint(name, constraint);
      }
      return scope;
   }
}
