package org.ternlang.tree.compile;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.type.Type;
import org.ternlang.tree.constraint.GenericList;

public class TypeScopeCompiler extends ScopeCompiler{
   
   protected final GenericList generics;
   
   public TypeScopeCompiler(GenericList generics) {
      this.generics = generics;
   }
   
   @Override
   public Scope define(Scope scope, Type type) throws Exception{
      List<Constraint> constraints = generics.getGenerics(scope);
      Scope outer = type.getScope();
      Scope inner = outer.getChild();
      ScopeState state = inner.getState();
      int size = constraints.size();

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);    
         String name = constraint.getName(inner);

         state.addConstraint(name, constraint);
      }
      return inner;
   }

   @Override
   public Scope compile(Scope scope, Type type, Function function) throws Exception {
      List<Constraint> constraints = generics.getGenerics(scope);
      Scope outer = type.getScope();
      Scope inner = outer.getChild();
      ScopeState state = inner.getState();
      int size = constraints.size();

      compileParameters(inner, function);
      compileProperties(inner, type);    

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);    
         String name = constraint.getName(inner);

         state.addConstraint(name, constraint);
      } 
      return inner;
   }
}
