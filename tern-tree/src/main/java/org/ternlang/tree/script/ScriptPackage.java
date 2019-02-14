package org.ternlang.tree.script;

import static org.ternlang.core.result.Result.NORMAL;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.link.FutureExecution;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public class ScriptPackage extends Statement {

   private final AtomicReference<Execution> reference;
   private final Statement[] statements;
   private final AtomicBoolean create;
   private final AtomicBoolean define;
   
   public ScriptPackage(Statement... statements) {
      this.reference = new AtomicReference<Execution>();
      this.create = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.statements = statements;
   }
   
   @Override
   public void create(Scope scope) throws Exception {
      if(create.compareAndSet(true, false)) {
         for(Statement statement : statements) {
            statement.create(scope);
         }
      }
   }
   
   @Override
   public boolean define(Scope scope) throws Exception {
      if(define.compareAndSet(true, false)) {
         for(Statement statement : statements) {
            statement.define(scope);
         }
      }
      return true;
   }
   
   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      Execution result = reference.get();
      
      if(result == null) {
         ScriptPackageTask job = new ScriptPackageTask(reference, scope, statements);
         FutureTask<Execution> task = new FutureTask<Execution>(job);
         FutureExecution execution = new FutureExecution(task, null);
         
         if(reference.compareAndSet(null, execution)) {
            task.run();
            return task.get();
         }
         return reference.get();
      }
      return result;
   }
   
   
   private static class ScriptPackageTask implements Callable<Execution> {
      
      private final AtomicReference<Execution> reference;
      private final Statement[] statements;
      private final Scope scope;
      
      public ScriptPackageTask(AtomicReference<Execution> reference, Scope scope, Statement... statements) {
         this.reference = reference;
         this.statements = statements;
         this.scope = scope;
      }
      
      @Override
      public Execution call() throws Exception {         
         Execution[] executions = new Execution[statements.length];
         Execution execution = new ScriptPackageExecution(executions);
         
         for(int i = 0; i < statements.length; i++) {
            executions[i] = statements[i].compile(scope, null);
            statements[i] = null;
         }
         reference.set(execution);
         return execution;
      }
   }
   
   private static class ScriptPackageExecution extends Execution {
      
      private final Execution[] statements;
      private final AtomicBoolean execute;
      
      public ScriptPackageExecution(Execution... statements) {
         this.execute = new AtomicBoolean(true);
         this.statements = statements;
      }
      
   
      @Override
      public Result execute(Scope scope) throws Exception {
         Result last = NORMAL;
         
         if(execute.compareAndSet(true, false)) {
            for(int i = 0; i < statements.length; i++) {
               Result result = statements[i].execute(scope);
               
               if(!result.isNormal()){
                  throw new InternalStateException("Illegal statement");
               }
               statements[i] = null;
               last = result;
            }
         }
         return last;
      }
   }
}