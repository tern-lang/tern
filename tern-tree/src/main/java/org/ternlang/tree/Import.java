package org.ternlang.tree;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Statement;
import org.ternlang.core.link.ExceptionStatement;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;

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