package org.ternlang.tree.function;

import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.bind.FunctionMatcher;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.NameReference;

public class FunctionHandle implements Compilation {   
   
   private final NameReference reference;
   
   public FunctionHandle(Evaluation identifier) {
      this.reference = new NameReference(identifier);
   }
   
   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      String name = reference.getName(scope);  
      FunctionBinder binder = context.getBinder(); 
      FunctionMatcher matcher = binder.bind(name); 
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return new CompileResult(matcher, name, true);
      }
      return new CompileResult(matcher, name);
   }

   private static class CompileResult extends Evaluation {
      
      private final FunctionHandleBuilder builder;
      private final String name;

      public CompileResult(FunctionMatcher matcher, String name) {
         this(matcher, name, false);         
      }
      
      public CompileResult(FunctionMatcher matcher, String name, boolean constructor) {
         this.builder = new FunctionHandleBuilder(matcher, constructor);
         this.name = name;
      }
   
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Module module = scope.getModule(); // is this the correct module?
         Function function = builder.create(module, left, name); 
         
         return Value.getTransient(function);
      }
   }
}