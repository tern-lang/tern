package tern.tree.function;

import static tern.core.Reserved.TYPE_CONSTRUCTOR;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.function.Function;
import tern.core.function.bind.FunctionBinder;
import tern.core.function.bind.FunctionMatcher;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.tree.NameReference;

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