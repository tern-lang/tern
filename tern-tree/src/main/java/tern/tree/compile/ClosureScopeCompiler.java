package tern.tree.compile;

import static tern.core.scope.extract.ScopePolicy.COMPILE_CLOSURE;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;
import tern.tree.constraint.GenericList;

public class ClosureScopeCompiler extends FunctionScopeCompiler{
   
   public ClosureScopeCompiler(GenericList generics) {
      super(generics, COMPILE_CLOSURE);
   }
   
   @Override
   public Scope compile(Scope closure, Type type, Function function) throws Exception {
      List<Constraint> constraints = generics.getGenerics(closure);
      Scope scope = extractor.extract(closure);
      Scope stack = scope.getStack();
      ScopeState state = stack.getState();
      int size = constraints.size();

      compileParameters(stack, function);
      compileProperties(stack, type);

      for(int i = 0; i < size; i++) {
         Constraint constraint = constraints.get(i);
         String name = constraint.getName(stack);

         state.addConstraint(name, constraint);
      }
      return stack;
   }
   
   public Scope extract(Scope closure, Type type) throws Exception {
      return extractor.extract(closure);
   }
}
