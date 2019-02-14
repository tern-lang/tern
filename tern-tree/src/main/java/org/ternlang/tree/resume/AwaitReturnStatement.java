package org.ternlang.tree.resume;

import static org.ternlang.core.result.Result.RETURN;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.result.Result;
import org.ternlang.core.resume.Promise;
import org.ternlang.core.resume.Resume;
import org.ternlang.core.resume.Yield;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.SuspendStatement;

public class AwaitReturnStatement implements Compilation {

   private final Statement control;

   public AwaitReturnStatement(Evaluation evaluation){
      this.control = new CompileResult(evaluation);
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

      private final Evaluation evaluation;

      public CompileResult(Evaluation evaluation) {
         this.evaluation = evaluation;
      }

      @Override
      public boolean define(Scope scope) throws Exception {
         if(evaluation != null) {
            evaluation.define(scope);
         }
         return true;
      }

      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         if(evaluation != null) {
            evaluation.compile(scope, null);
         }
         return new CompileExecution(evaluation);
      }
   }

   private static class CompileExecution extends SuspendStatement<Value> {

      private final Evaluation evaluation;

      public CompileExecution(Evaluation evaluation){
         this.evaluation = evaluation;
      }

      @Override
      public Result execute(Scope scope) throws Exception {
         Result result = Result.getAwait(null, scope, this);
         Yield value = result.getValue();

         return suspend(scope, result, this, null);
      }

      @Override
      public Result resume(Scope scope, Value state) throws Exception {
         if(state == null) {
            Value value = evaluation.evaluate(scope, null);
            Object object = value.getValue();

            if (object != null) {
               if(Promise.class.isInstance(object)) {
                  Result result = Result.getAwait(object, scope, this);
                  return suspend(scope, result, this, value);
               }
            }
            return execute(scope, value);
         }
         return execute(scope, state);
      }

      private Result execute(Scope scope, Value state) throws Exception {
         Object result = state.getValue();

         if(result != null) {
            return Result.getReturn(result);
         }
         return RETURN;
      }


      @Override
      public Resume suspend(Result result, Resume resume, Value object) throws Exception {
         return new AwaitResume(resume, object);
      }
   }
}
