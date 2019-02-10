package tern.tree.operation;

import static tern.core.result.Result.NORMAL;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Execution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.error.InternalStateException;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.core.variable.Value;

public class AssignmentListStatement implements Compilation {
   
   private final Statement assignment;
   
   public AssignmentListStatement(AssignmentList left, Evaluation... right) {
      this.assignment = new CompileResult(left, right);
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

      private final AssignmentList left;
      private final Evaluation[] right;
      
      public CompileResult(AssignmentList left, Evaluation... right) {
         this.right = right;
         this.left = left;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         int count = left.define(scope);
         
         if(count < right.length) {
            int size = right.length - count;
            
            if(size == 1) {
               throw new InternalStateException("No assignment for last variable");
            }
            throw new InternalStateException("No assignments for " + size + " variables");
         }
         for(int i = 0; i < right.length; i++) {
            right[i].define(scope);
         }
         return true; 
      }


      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Constraint[] constraints = left.compile(scope);
         
         for(int i = 0; i < right.length; i++) {
            Constraint constraint = constraints[i];
            Evaluation value = right[i];
            
            value.compile(scope, constraint);
         }
         return new CompileExecution(left, right);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final AssignmentList left;
      private final Evaluation[] right;
      
      public CompileExecution(AssignmentList left, Evaluation... right) {
         this.right = right;
         this.left = left;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value[] values = left.evaluate(scope);
         
         if(values.length > 0) {
            Object[] results = new Object[values.length];
            
            for(int i = 0; i < right.length; i++) {
               Value result = right[i].evaluate(scope, null);
               Object object = result.getValue();
               
               if(i < results.length) {
                  results[i] = object;
               }
            }
            for(int i = 0; i < results.length; i++) {
               Object object = results[i];
               Value value = values[i];
               
               value.setValue(object);
            }            
         }
         return NORMAL;
      }
   }
}
