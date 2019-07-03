package org.ternlang.tree.function;

import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.variable.Value.NULL;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.ArgumentList;

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
      public void define(Scope scope) throws Exception {
         arguments.define(scope);
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
