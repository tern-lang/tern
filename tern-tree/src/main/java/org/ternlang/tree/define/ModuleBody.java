package org.ternlang.tree.define;

import static org.ternlang.core.result.Result.NORMAL;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.link.ExceptionExecution;
import org.ternlang.core.link.FutureExecution;
import org.ternlang.core.module.Module;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public class ModuleBody extends Statement {

   private final AtomicReference<Execution> reference;
   private final Statement[] statements;
   private final Statement[] executable;
   private final ModulePart[] parts;
   private final AtomicBoolean create;
   private final AtomicBoolean define;

   public ModuleBody(ModulePart... parts) {
      this.reference = new AtomicReference<Execution>();
      this.statements = new Statement[parts.length];
      this.executable = new Statement[parts.length];
      this.define = new AtomicBoolean(true);
      this.create = new AtomicBoolean(true);
      this.parts = parts;
   }

   @Override
   public void create(Scope scope) throws Exception {
      if(create.compareAndSet(true, false)) {
         Module module = scope.getModule();

         for(int i = 0; i < parts.length; i++) {
            ModulePart part = parts[i];
            Statement statement = part.define(this, module);

            statement.create(scope);
            statements[i] = statement;
            parts[i] = null;
         }
      }
   }

   @Override
   public boolean define(Scope scope) throws Exception {
      if(define.compareAndSet(true, false)) {
         for(int i = 0; i < statements.length; i++) {
            Statement statement = statements[i];

            if(statement.define(scope)){
               executable[i] = statement;
               statements[i] = null;
            }
         }
      }
      return true;
   }

   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      Execution result = reference.get();
      Module module = scope.getModule();

      if(result == null) {
         ModuleTask job = new ModuleTask(scope, module, statements, executable);
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

   private class ModuleTask implements Callable<Execution> {

      private final Statement[] executable;
      private final Statement[] statements;
      private final Module module;
      private final Scope scope;

      public ModuleTask(Scope scope, Module module, Statement[] statements, Statement[] executable) {
         this.statements = statements;
         this.executable = executable;
         this.module = module;
         this.scope = scope;
      }

      @Override
      public Execution call() throws Exception {
         Execution[] executions = new Execution[statements.length];
         Execution execution = new ModuleExecution(scope, executions);

         try {
            for (int i = 0; i < executable.length; i++) {
               Statement statement = executable[i];

               if (statement != null) {
                  executions[i] = statement.compile(scope, null);
               }
            }
            for (int i = 0; i < statements.length; i++) {
               Statement statement = statements[i];

               if (statement != null) {
                  executions[i] = statement.compile(scope, null);
               }
            }
            reference.set(execution);
         } catch(Exception e) {
            return new ExceptionExecution("Error occurred compiling '" + module + "'", e);
         }
         return execution;
      }
   }


   private class ModuleExecution extends Execution {

      private final Execution[] executions;
      private final AtomicBoolean execute;
      private final Scope module;

      public ModuleExecution(Scope module, Execution[] executions) {
         this.execute = new AtomicBoolean(true);
         this.executions = executions;
         this.module = module;
      }

      @Override
      public Result execute(Scope scope) throws Exception {
         Result last = NORMAL;

         if(execute.compareAndSet(true, false)) {
            Module parent = module.getModule();
            Scope root = parent.getScope();

            for(int i = 0; i < executions.length; i++) {
               Result result = executions[i].execute(root);

               if(!result.isNormal()){
                  return result;
               }
               last = result;
            }
         }
         return last;
      }
   }
}