package tern.tree.compile;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;
import tern.tree.constraint.GenericList;

public class TypeScopeCompiler extends ScopeCompiler{
   
   protected final GenericList generics;
   
   public TypeScopeCompiler(GenericList generics) {
      this.generics = generics;
   }
   
   @Override
   public Scope define(Scope scope, Type type) throws Exception{
      List<Constraint> constraints = generics.getGenerics(scope);
      Scope outer = type.getScope();
      Scope inner = outer.getStack();
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
      Scope inner = outer.getStack();
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
