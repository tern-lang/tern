package org.ternlang.tree.resume;

import static org.ternlang.core.result.Result.NORMAL;

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
import org.ternlang.parse.StringToken;
import org.ternlang.tree.SuspendStatement;
import org.ternlang.tree.operation.AssignmentOperation;

public class AwaitStatement implements Compilation {

   private final Statement control;

   public AwaitStatement(Evaluation right){
      this(null, null, right);
   }

   public AwaitStatement(Evaluation left, Evaluation right){
      this(left, null, right);
   }

   public AwaitStatement(Evaluation left, StringToken token, Evaluation right){
      this.control = new CompileResult(left, token, right);
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

      private final StringToken token;
      private final Evaluation right;
      private final Evaluation left;

      public CompileResult(Evaluation left, StringToken token, Evaluation right){
         this.token = token;
         this.right = right;
         this.left = left;
      }

      @Override
      public boolean define(Scope scope) throws Exception {
         if(left != null) {
            left.define(scope);
         }
         if(right != null) {
            right.define(scope);
         }
         return true;
      }

      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         if(left != null) {
            left.compile(scope, null);
         }
         if(right != null) {
            right.compile(scope, null);
         }
         return new CompileExecution(left, token, right);
      }
   }

   private static class CompileExecution extends SuspendStatement<Value> {

      private final AssignmentOperation operation;
      private final Evaluation right;
      private final Evaluation left;

      public CompileExecution(Evaluation left, StringToken token, Evaluation right){
         this.operation = new AssignmentOperation(token);
         this.right = right;
         this.left = left;
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
            Value value = right.evaluate(scope, null);
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

         if (left != null) {
            Value assign = left.evaluate(scope, null);
            Value value = operation.operate(scope, assign, state);

            if (value != null) {
               Object object = value.getValue();

               if(object != null) {
                  return Result.getNormal(object);
               }
               return NORMAL;
            }
         }
         return Result.getNormal(result);
      }


      @Override
      public Resume suspend(Result result, Resume resume, Value object) throws Exception {
         return new AwaitResume(resume, object);
      }
   }
}