package tern.tree;

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
import tern.core.scope.instance.Instance;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.core.variable.Value;
import tern.core.resume.Resume;
import tern.core.resume.Yield;

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