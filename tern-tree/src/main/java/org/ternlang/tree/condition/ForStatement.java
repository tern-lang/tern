package org.ternlang.tree.condition;

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
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.variable.Value;
import org.ternlang.core.resume.Resume;
import org.ternlang.core.resume.Yield;
import org.ternlang.tree.SuspendStatement;

public class ForStatement implements Compilation {
   
   private final Statement loop;
   
   public ForStatement(Statement declaration, Evaluation evaluation, Statement statement) {
      this(declaration, evaluation, null, statement);
   }
   
   public ForStatement(Statement declaration, Evaluation evaluation, Evaluation assignment, Statement statement) {
      this.loop = new CompileResult(declaration, evaluation, assignment, statement);
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
      private final Statement declaration;
      private final Evaluation assignment;
      private final Statement body;

      public CompileResult(Statement declaration, Evaluation condition, Evaluation assignment, Statement body) {
         this.declaration = declaration;
         this.assignment = assignment;
         this.condition = condition;
         this.body = body;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         ScopeIndex index = scope.getIndex();
         int size = index.size();
         
         try {
            declaration.define(scope);
            condition.define(scope);
            
            if(assignment != null) {
               assignment.define(scope);
            }
            body.define(scope);
         } finally {
            index.reset(size);
         }
         return true;
      }

      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         ScopeIndex index = scope.getIndex();
         int size = index.size();
         
         try {            
            Execution variable = declaration.compile(scope, null);
            Execution execution = body.compile(scope, returns);
            
            condition.compile(scope, null);
            
            if(assignment != null) {
               assignment.compile(scope, null);
            }
            return new CompileExecution(variable, condition, assignment, execution);
         } finally {
            index.reset(size);
         }
      }
   }   
   
   private static class CompileExecution extends SuspendStatement<Object> {

      private final Evaluation condition;
      private final Execution declaration;
      private final Evaluation assignment;
      private final Execution body;

      public CompileExecution(Execution declaration, Evaluation condition, Evaluation assignment, Execution body) {
         this.declaration = declaration;
         this.assignment = assignment;
         this.condition = condition;
         this.body = body;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         declaration.execute(scope);
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
                  return suspend(scope, next, this, assignment);
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
            if(assignment != null) {
               assignment.evaluate(scope, null);
            }
         }
      }

      @Override
      public Resume suspend(Result result, Resume resume, Object value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new ForResume(child, resume, assignment);
      }
   }
}