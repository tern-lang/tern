package org.ternlang.tree;

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
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.core.variable.Value;
import org.ternlang.core.resume.Resume;
import org.ternlang.core.resume.Yield;

public class SynchronizedStatement implements Compilation {
   
   private final Statement statement;
   
   public SynchronizedStatement(Evaluation evaluation, Statement statement) {
      this.statement = new CompileResult(evaluation, statement);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, statement, trace);
   }
   
   private static class CompileResult extends Statement {

      private final Statement statement;
      private final Evaluation reference;
      
      public CompileResult(Evaluation reference, Statement statement) {
         this.statement = statement;
         this.reference = reference;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         reference.define(scope);
         statement.define(scope);
         return true;
      }

      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Constraint constraint = reference.compile(scope, null);
         Execution execution = statement.compile(scope, returns);
         
         return new CompileExecution(reference, execution);
      }
   }
   
   private static class CompileExecution extends SuspendStatement<Resume> {

      private final StatementResume statement;
      private final Evaluation reference;
      
      public CompileExecution(Evaluation reference, Execution statement) {
         this.statement = new StatementResume(statement);
         this.reference = reference;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         return resume(scope, statement);
      }
      
      @Override
      public Result resume(Scope scope, Resume statement) throws Exception {
         Object object = resolve(scope);
         
         synchronized(object) {
            Result result = statement.resume(scope, null);
            
            if(result.isYield()) {
               return suspend(scope, result, this, null);
            }
            return result;
         }
      }

      @Override
      public Resume suspend(Result result, Resume resume, Resume value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new SynchronizedResume(this, child);
      }   
      
      private Object resolve(Scope scope) throws Exception {
         Value value = reference.evaluate(scope, null);
         Object object = value.getValue();
         
         if(Instance.class.isInstance(object)) {
            Instance instance = (Instance)object;
            Object bridge = instance.getBridge();
            
            if(bridge != null) {
               return bridge;
            }
         }
         return object;
      }   
   }
}