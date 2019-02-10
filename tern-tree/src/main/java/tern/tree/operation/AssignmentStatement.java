package tern.tree.operation;

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
import tern.parse.StringToken;

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