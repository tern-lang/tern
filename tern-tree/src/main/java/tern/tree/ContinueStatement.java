package tern.tree;

import static tern.core.result.Result.CONTINUE;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Execution;
import tern.core.NoExecution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.parse.StringToken;

public class ContinueStatement implements Compilation {
   
   private final Statement control;
   
   public ContinueStatement(StringToken token){
      this.control = new CompileResult();
   }   
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, control, trace);
   }
   
   private static class CompileResult extends Statement {
      
      private final Execution execution;
      
      public CompileResult() {
         this.execution = new NoExecution(CONTINUE);
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         return execution;
      }
   }
}