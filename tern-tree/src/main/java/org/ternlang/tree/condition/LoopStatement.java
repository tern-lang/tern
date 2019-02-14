package org.ternlang.tree.condition;

import static org.ternlang.core.result.Result.NORMAL;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.resume.Resume;
import org.ternlang.core.resume.Yield;
import org.ternlang.tree.SuspendStatement;

public class LoopStatement implements Compilation {
   
   private final Statement loop;
   
   public LoopStatement(Statement statement) {
      this.loop = new CompileResult(statement);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, loop, trace);
   }
   
   private static class CompileResult extends Statement {
      
      private final Statement body;
      
      public CompileResult(Statement body) {
         this.body = body;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {   
         body.define(scope);
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Execution execution = body.compile(scope, returns);
         return new CompileExecution(execution);
      }
   }
   
   
   private static class CompileExecution extends SuspendStatement<Object> {
      
      private final Execution body;
      
      public CompileExecution(Execution body) {
         this.body = body;
      }
   
      @Override
      public Result execute(Scope scope) throws Exception {
         return resume(scope, null);
      }
      
      @Override
      public Result resume(Scope scope, Object data) throws Exception {
         while(true) {
            Result result = body.execute(scope);
            
            if(result.isYield()) {
               return suspend(scope, result, this, null);
            }
            if(result.isReturn()) {
               return result;
            }
            if(result.isBreak()) {
               return NORMAL;
            }
         }
      }

      @Override
      public Resume suspend(Result result, Resume resume, Object value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new LoopResume(child, resume);
      }
   }
}