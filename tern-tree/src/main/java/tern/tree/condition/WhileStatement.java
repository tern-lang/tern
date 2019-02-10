package tern.tree.condition;

import static tern.core.result.Result.NORMAL;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Execution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.core.variable.Value;
import tern.core.resume.Resume;
import tern.core.resume.Yield;
import tern.tree.SuspendStatement;

public class WhileStatement implements Compilation {
   
   private final Statement loop;
   
   public WhileStatement(Evaluation evaluation, Statement body) {
      this.loop = new CompileResult(evaluation, body);
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
   
      private final Evaluation condition;
      private final Statement body;
      
      public CompileResult(Evaluation condition, Statement body) {
         this.condition = condition;
         this.body = body;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception { 
         condition.define(scope);
         body.define(scope);
         return true;
      }
   
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Constraint constraint = condition.compile(scope, null);
         Execution execution = body.compile(scope, returns);
         
         return new CompileExecution(condition, execution);   
      }
   }
   
   private static class CompileExecution extends SuspendStatement<Object> {
      
      private final Evaluation condition;
      private final Execution body;
      
      public CompileExecution(Evaluation condition, Execution body) {
         this.condition = condition;
         this.body = body;
      }

      @Override
      public Result execute(Scope scope) throws Exception {
         return resume(scope, null);
      }
      
      @Override
      public Result resume(Scope scope, Object data) throws Exception {
         while(true) {
            Value value = condition.evaluate(scope, null);
            Object result = value.getValue();
            
            if(BooleanChecker.isTrue(result)) {
               Result next = body.execute(scope);
               
               if(next.isYield()) {
                  return suspend(scope, next, this, null);
               }
               if(next.isReturn()) {
                  return next;
               }
               if(next.isBreak()) {
                  return NORMAL;
               }
            } else {
               return NORMAL;
            } 
         }
      }

      @Override
      public Resume suspend(Result result, Resume resume, Object value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new WhileResume(child, resume);
      }
   }
}