package tern.tree.function;

import static tern.core.constraint.Constraint.NONE;
import static tern.core.variable.Value.NULL;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionResolver;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.tree.ArgumentList;

public class FunctionCurry implements Compilation {
   
   private final ArgumentList arguments;
   
   public FunctionCurry(ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      FunctionResolver resolver = context.getResolver();
      
      return new CompileResult(resolver, arguments);
   }

   private static class CompileResult extends Evaluation {
   
      private final FunctionResolver resolver;
      private final ArgumentList arguments;
      
      public CompileResult(FunctionResolver resolver, ArgumentList arguments) {
         this.arguments = arguments;
         this.resolver = resolver;
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {         
         arguments.compile(scope);
         return NONE;
      }
         
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {         
         Object[] array = arguments.create(scope); 
         FunctionCall call = resolver.resolveValue(left, array);
         Object object = left.getValue();
         int width = array.length;
         
         if(call == null) {
            throw new InternalStateException("Result was not a closure of " + width +" arguments");
         }
         Object result = call.invoke(scope, object, array);
         
         if(result != null) {
            return Value.getTransient(result);
         }
         return NULL;
         
      }
   }
}
