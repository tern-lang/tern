package tern.tree;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Statement;
import tern.core.link.ExceptionStatement;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;

public class Import implements Compilation {

   private final ImportBuilder builder;  
   
   public Import(Qualifier qualifier) {
      this(qualifier, null);
   }
   
   public Import(Qualifier qualifier, Evaluation alias) {
      this.builder = new ImportBuilder(qualifier, alias);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getImport(module, path, line);
      
      try {
         return builder.create(module, path, line);
      } catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
         return new ExceptionStatement("Could not process import", cause);
      }
   }
}