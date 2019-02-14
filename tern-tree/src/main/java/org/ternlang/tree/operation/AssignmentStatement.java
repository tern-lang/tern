package org.ternlang.tree.operation;

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
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

public class AssignmentStatement implements Compilation {
   
   private final Statement assignment;
   
   public AssignmentStatement(Evaluation reference, StringToken token, Evaluation value) {
      this.assignment = new CompileResult(reference, token, value);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, assignment, trace);
   }

   private static class CompileResult extends Statement {
      
      private final Evaluation assignment;
      
      public CompileResult(Evaluation left, StringToken token, Evaluation right) {
         this.assignment = new Assignment(left, token, right);
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         assignment.define(scope);
         return false;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         assignment.compile(scope, null);
         return new CompileExecution(assignment);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation assignment;
      
      public CompileExecution(Evaluation assignment) {
         this.assignment = assignment;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value reference = assignment.evaluate(scope, null);
         Object value = reference.getValue();
         
         return Result.getNormal(value);
      }
   }
}